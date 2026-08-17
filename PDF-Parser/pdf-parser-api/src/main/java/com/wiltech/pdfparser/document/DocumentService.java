package com.wiltech.pdfparser.document;

import com.wiltech.pdfparser.document.content.DocumentContentApplicationService;
import com.wiltech.pdfparser.document.parsing.DocumentParsing;
import com.wiltech.pdfparser.document.parsing.DocumentParsingRequestedEvent;
import com.wiltech.pdfparser.document.parsing.DocumentParsingService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

	private final DocumentApplicationService documentApplicationService;
	private final DocumentContentApplicationService documentContentApplicationService;
	private final DocumentParsingService documentParsingService;
	private final ApplicationEventPublisher eventPublisher;

	public DocumentService(DocumentApplicationService documentApplicationService,
			DocumentContentApplicationService documentContentApplicationService,
			DocumentParsingService documentParsingService, ApplicationEventPublisher eventPublisher) {
		this.documentApplicationService = documentApplicationService;
		this.documentContentApplicationService = documentContentApplicationService;
		this.documentParsingService = documentParsingService;
		this.eventPublisher = eventPublisher;
	}

	public List<DocumentDto> getAll() {
		return documentApplicationService.getAll().stream()
				.map(this::toDto)
				.toList();
	}

	public DocumentDto getById(UUID id) {
		return toDto(documentApplicationService.getById(id));
	}

	@Transactional
	public DocumentDto upload(String name, String type, long size, byte[] data) {
		Document document = documentApplicationService.create(new CreateDocumentRequest(name, type, size));
		documentContentApplicationService.save(document.getId(), data);
		documentParsingService.create(document.getId());
		eventPublisher.publishEvent(new DocumentParsingRequestedEvent(document.getId(), data));
		return toDto(document);
	}

	@Transactional
	public void delete(UUID id) {
		documentParsingService.delete(id);
		documentContentApplicationService.delete(id);
		documentApplicationService.delete(id);
	}

	private DocumentDto toDto(Document document) {
		DocumentParsing parsing = documentParsingService.getByDocumentId(document.getId());
		return DocumentDto.from(document, parsing.getStatus(), parsing.getParsedText());
	}
}
