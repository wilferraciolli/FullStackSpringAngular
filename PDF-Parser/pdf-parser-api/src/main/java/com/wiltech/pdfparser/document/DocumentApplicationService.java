package com.wiltech.pdfparser.document;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentApplicationService {

	private final DocumentRepository documentRepository;

	public DocumentApplicationService(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	public List<DocumentDto> getAll() {
		return documentRepository.findAll().stream()
				.map(DocumentDto::from)
				.toList();
	}

	public DocumentDto getById(UUID id) {
		return DocumentDto.from(findDocumentOrThrow(id));
	}

	public DocumentDto create(CreateDocumentRequest request) {
		Document document = new Document(request.name(), request.type(), request.size());
		return DocumentDto.from(documentRepository.save(document));
	}

	public void delete(UUID id) {
		documentRepository.delete(findDocumentOrThrow(id));
	}

	private Document findDocumentOrThrow(UUID id) {
		return documentRepository.findById(id)
				.orElseThrow(() -> new DocumentNotFoundException(id));
	}
}
