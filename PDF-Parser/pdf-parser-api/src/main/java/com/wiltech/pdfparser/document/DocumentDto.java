package com.wiltech.pdfparser.document;

import com.wiltech.pdfparser.document.parsing.DocumentParsingStatus;

import java.time.Instant;
import java.util.UUID;

public record DocumentDto(UUID id, String name, String type, long size, Instant createdAt,
		DocumentParsingStatus statusCode, String parsedText) {

	static DocumentDto from(Document document, DocumentParsingStatus statusCode, String parsedText) {
		return new DocumentDto(document.getId(), document.getName(), document.getType(), document.getSize(),
				document.getCreatedAt(), statusCode, parsedText);
	}
}
