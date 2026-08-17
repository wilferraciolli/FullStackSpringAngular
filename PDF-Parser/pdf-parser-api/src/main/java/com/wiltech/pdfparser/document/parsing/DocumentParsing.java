package com.wiltech.pdfparser.document.parsing;

import com.wiltech.pdfparser.document.Document;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "document_parsing")
public class DocumentParsing {

	@Id
	private UUID documentId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "document_id")
	private Document document;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DocumentParsingStatus status;

	@Lob
	private String parsedText;

	protected DocumentParsing() {
	}

	public DocumentParsing(Document document, DocumentParsingStatus status) {
		this.document = document;
		this.status = status;
	}

	public UUID getDocumentId() {
		return documentId;
	}

	public DocumentParsingStatus getStatus() {
		return status;
	}

	public String getParsedText() {
		return parsedText;
	}

	public void updateStatus(DocumentParsingStatus status) {
		this.status = status;
	}

	public void updateResult(DocumentParsingStatus status, String parsedText) {
		this.status = status;
		this.parsedText = parsedText;
	}
}
