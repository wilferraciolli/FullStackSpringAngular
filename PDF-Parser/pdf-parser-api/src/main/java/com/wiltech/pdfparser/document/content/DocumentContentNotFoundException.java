package com.wiltech.pdfparser.document.content;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentContentNotFoundException extends RuntimeException {

	public DocumentContentNotFoundException(UUID documentId) {
		super("Document content not found: " + documentId);
	}
}
