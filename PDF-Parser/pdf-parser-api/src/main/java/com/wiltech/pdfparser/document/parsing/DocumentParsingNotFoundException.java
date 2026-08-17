package com.wiltech.pdfparser.document.parsing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentParsingNotFoundException extends RuntimeException {

	public DocumentParsingNotFoundException(UUID documentId) {
		super("Document parsing not found: " + documentId);
	}
}
