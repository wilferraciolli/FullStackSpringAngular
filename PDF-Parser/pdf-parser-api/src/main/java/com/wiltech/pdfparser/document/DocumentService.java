package com.wiltech.pdfparser.document;

import com.wiltech.pdfparser.document.content.DocumentContentApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DocumentService {

	private final DocumentApplicationService documentApplicationService;
	private final DocumentContentApplicationService documentContentApplicationService;

	public DocumentService(DocumentApplicationService documentApplicationService,
			DocumentContentApplicationService documentContentApplicationService) {
		this.documentApplicationService = documentApplicationService;
		this.documentContentApplicationService = documentContentApplicationService;
	}

	@Transactional
	public DocumentDto upload(String name, String type, long size, byte[] data) {
		DocumentDto created = documentApplicationService.create(new CreateDocumentRequest(name, type, size));
		documentContentApplicationService.save(created.id(), data);
		return created;
	}

	@Transactional
	public void delete(UUID id) {
		documentContentApplicationService.delete(id);
		documentApplicationService.delete(id);
	}
}
