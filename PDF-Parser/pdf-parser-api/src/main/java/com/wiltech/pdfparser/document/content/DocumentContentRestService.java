package com.wiltech.pdfparser.document.content;

import com.wiltech.pdfparser.document.DocumentDto;
import com.wiltech.pdfparser.document.DocumentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/documents")
public class DocumentContentRestService {

	private final DocumentService documentService;

	public DocumentContentRestService(DocumentService documentService) {
		this.documentService = documentService;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DocumentDto> upload(@RequestParam("file") MultipartFile file) throws IOException {
		DocumentDto created = documentService.upload(file.getOriginalFilename(), file.getContentType(), file.getSize(),
				file.getBytes());
		return ResponseEntity.created(URI.create("/api/documents/" + created.id())).body(created);
	}
}
