package com.wiltech.pdfparser.document;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestService {

	private final DocumentApplicationService documentApplicationService;
	private final DocumentService documentService;

	public DocumentRestService(DocumentApplicationService documentApplicationService, DocumentService documentService) {
		this.documentApplicationService = documentApplicationService;
		this.documentService = documentService;
	}

	@GetMapping
	public List<DocumentDto> getAll() {
		return documentApplicationService.getAll();
	}

	@GetMapping("/{id}")
	public DocumentDto getById(@PathVariable UUID id) {
		return documentApplicationService.getById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		documentService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
