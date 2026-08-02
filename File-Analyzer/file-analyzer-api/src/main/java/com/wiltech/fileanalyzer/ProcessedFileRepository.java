package com.wiltech.fileanalyzer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessedFileRepository extends JpaRepository<ProcessedFile, UUID> {
}
