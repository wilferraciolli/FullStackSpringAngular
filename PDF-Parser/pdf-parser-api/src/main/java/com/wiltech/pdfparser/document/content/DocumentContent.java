package com.wiltech.pdfparser.document.content;

import com.wiltech.pdfparser.document.Document;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "document_content")
public class DocumentContent {

	@Id
	private UUID documentId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "document_id")
	private Document document;

	@Lob
	@Column(nullable = false)
	private byte[] data;

	protected DocumentContent() {
	}

	public DocumentContent(Document document, byte[] data) {
		this.document = document;
		this.data = data;
	}

	public UUID getDocumentId() {
		return documentId;
	}

	public byte[] getData() {
		return data;
	}
}
