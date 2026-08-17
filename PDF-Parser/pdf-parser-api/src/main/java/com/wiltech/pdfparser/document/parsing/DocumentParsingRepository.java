package com.wiltech.pdfparser.document.parsing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentParsingRepository extends JpaRepository<DocumentParsing, UUID> {
}
