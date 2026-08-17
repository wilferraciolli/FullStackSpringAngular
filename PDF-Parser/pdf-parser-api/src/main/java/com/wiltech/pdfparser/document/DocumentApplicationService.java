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

	public List<Document> getAll() {
		return documentRepository.findAll();
	}

	public Document getById(UUID id) {
		return documentRepository.findById(id)
				.orElseThrow(() -> new DocumentNotFoundException(id));
	}

	public Document create(CreateDocumentRequest request) {
		Document document = new Document(request.name(), request.type(), request.size());
		return documentRepository.save(document);
	}

	public void delete(UUID id) {
		documentRepository.delete(getById(id));
	}
}
