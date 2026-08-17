package com.wiltech.pdfparser.document;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

	private final DocumentService documentService;

	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@GetMapping
	public List<DocumentDto> getAll() {
		return documentService.getAll();
	}

	@GetMapping("/{id}")
	public DocumentDto getById(@PathVariable UUID id) {
		return documentService.getById(id);
	}

	@PostMapping
	public ResponseEntity<DocumentDto> create(@Valid @RequestBody CreateDocumentRequest request) {
		DocumentDto created = documentService.create(request);
		return ResponseEntity.created(URI.create("/api/documents/" + created.id())).body(created);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		documentService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
