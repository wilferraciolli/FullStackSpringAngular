package com.wiltech.pdfparser.document.content;

import com.wiltech.pdfparser.document.Document;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DocumentContentService {

	private final DocumentContentRepository documentContentRepository;

	public DocumentContentService(DocumentContentRepository documentContentRepository) {
		this.documentContentRepository = documentContentRepository;
	}

	public byte[] getContent(UUID documentId) {
		return documentContentRepository.findById(documentId)
				.map(DocumentContent::getData)
				.orElseThrow(() -> new DocumentContentNotFoundException(documentId));
	}

	public void save(Document document, byte[] data) {
		documentContentRepository.save(new DocumentContent(document, data));
	}

	public void delete(UUID documentId) {
		documentContentRepository.deleteById(documentId);
	}
}
