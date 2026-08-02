package com.wiltech.fileanalyzer;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class FileService {

    private final ProcessedFileRepository repository;

    public FileService(ProcessedFileRepository repository) {
        this.repository = repository;
    }

    public ProcessedFile processPdf(MultipartFile file) throws IOException {
        String text = extractText(file);
        
        // Basic conversion to JSON (simulated for now, can be improved with regex or AI)
        String json = convertTextToJson(text);

        ProcessedFile processedFile = ProcessedFile.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .rawText(text)
                .extractedData(json)
                .processedAt(LocalDateTime.now())
                .build();

        return repository.save(processedFile);
    }

    public java.util.List<ProcessedFile> getAllFiles() {
        return repository.findAll();
    }

    public java.util.Optional<ProcessedFile> getFileById(java.util.UUID id) {
        return repository.findById(id);
    }

    private String extractText(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String convertTextToJson(String text) {
        // For a blood test, we might look for common patterns like "Glucose: 90 mg/dL"
        // This is a placeholder for actual parsing logic
        return "{\"raw_extracted_text\": \"" + text.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "") + "\"}";
    }
}
