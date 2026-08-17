package com.wiltech.pdfparser.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "documents")
public class Document {

	@Id
	@GeneratedValue
	private UUID id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private long size;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	protected Document() {
	}

	public Document(String name, String type, long size) {
		this.name = name;
		this.type = type;
		this.size = size;
	}

	@PrePersist
	void onCreate() {
		this.createdAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public long getSize() {
		return size;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
