package com.wiltech.pdfparser.document;

import java.time.Instant;
import java.util.UUID;

public record DocumentDto(UUID id, String name, String type, long size, Instant createdAt) {

	static DocumentDto from(Document document) {
		return new DocumentDto(document.getId(), document.getName(), document.getType(), document.getSize(),
				document.getCreatedAt());
	}
}
