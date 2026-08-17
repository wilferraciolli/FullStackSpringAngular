package com.wiltech.pdfparser.document.content;

import com.wiltech.pdfparser.document.Document;
import com.wiltech.pdfparser.document.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DocumentContentApplicationService {

	private final DocumentContentRepository documentContentRepository;
	private final DocumentRepository documentRepository;

	public DocumentContentApplicationService(DocumentContentRepository documentContentRepository,
			DocumentRepository documentRepository) {
		this.documentContentRepository = documentContentRepository;
		this.documentRepository = documentRepository;
	}

	public byte[] getContent(UUID documentId) {
		return documentContentRepository.findById(documentId)
				.map(DocumentContent::getData)
				.orElseThrow(() -> new DocumentContentNotFoundException(documentId));
	}

	public void save(UUID documentId, byte[] data) {
		Document document = documentRepository.getReferenceById(documentId);
		documentContentRepository.save(new DocumentContent(document, data));
	}

	public void delete(UUID documentId) {
		documentContentRepository.deleteById(documentId);
	}
}
