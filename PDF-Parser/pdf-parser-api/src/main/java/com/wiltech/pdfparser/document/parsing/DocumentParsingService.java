package com.wiltech.pdfparser.document.parsing;

import com.wiltech.pdfparser.document.Document;
import com.wiltech.pdfparser.document.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.UUID;

@Service
public class DocumentParsingService {

	private static final Logger log = LoggerFactory.getLogger(DocumentParsingService.class);

	private final DocumentParsingRepository documentParsingRepository;
	private final DocumentRepository documentRepository;
	private final RestClient restClient;

	public DocumentParsingService(DocumentParsingRepository documentParsingRepository,
			DocumentRepository documentRepository, @Value("${pdf.ocr.service-url}") String ocrServiceUrl) {
		this.documentParsingRepository = documentParsingRepository;
		this.documentRepository = documentRepository;

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(Duration.ofSeconds(5));
		requestFactory.setReadTimeout(Duration.ofMinutes(2));

		this.restClient = RestClient.builder().baseUrl(ocrServiceUrl).requestFactory(requestFactory).build();
	}

	public DocumentParsing getByDocumentId(UUID documentId) {
		return documentParsingRepository.findById(documentId)
				.orElseThrow(() -> new DocumentParsingNotFoundException(documentId));
	}

	public void create(UUID documentId) {
		Document document = documentRepository.getReferenceById(documentId);
		documentParsingRepository.save(new DocumentParsing(document, DocumentParsingStatus.FILE_UPLOADED));
	}

	public void delete(UUID documentId) {
		documentParsingRepository.deleteById(documentId);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onDocumentParsingRequested(DocumentParsingRequestedEvent event) {
		parse(event.documentId(), event.data());
	}

	private void parse(UUID documentId, byte[] data) {
		updateStatus(documentId, DocumentParsingStatus.PROCESSING);

		try {
			ParseResponse response = callOcrService(data);
			String text = response.text();

			if (text == null || text.isBlank()) {
				updateResult(documentId, DocumentParsingStatus.INSUFFICIENT_DATA, text);
			} else {
				updateResult(documentId, DocumentParsingStatus.OK, text);
			}
		} catch (Exception e) {
			log.warn("Parsing failed for document {}", documentId, e);
			updateStatus(documentId, DocumentParsingStatus.FAILED);
		}
	}

	private ParseResponse callOcrService(byte[] data) {
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", new ByteArrayResource(data) {
			@Override
			public String getFilename() {
				return "document.pdf";
			}
		});

		return restClient.post()
				.uri("/parse")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.body(body)
				.retrieve()
				.body(ParseResponse.class);
	}

	private void updateStatus(UUID documentId, DocumentParsingStatus status) {
		DocumentParsing parsing = getByDocumentId(documentId);
		parsing.updateStatus(status);
		documentParsingRepository.save(parsing);
	}

	private void updateResult(UUID documentId, DocumentParsingStatus status, String parsedText) {
		DocumentParsing parsing = getByDocumentId(documentId);
		parsing.updateResult(status, parsedText);
		documentParsingRepository.save(parsing);
	}

	private record ParseResponse(String text, int pageCount, boolean usedOcr) {
	}
}
