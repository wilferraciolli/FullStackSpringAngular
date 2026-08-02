package com.wiltech.fileanalyzer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend to call
public class FileUploadController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ProcessedFile> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (!"application/pdf".equals(file.getContentType())) {
            return ResponseEntity.badRequest().build();
        }

        ProcessedFile processedFile = fileService.processPdf(file);
        return ResponseEntity.ok(processedFile);
    }

    @GetMapping
    public ResponseEntity<List<ProcessedFile>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessedFile> getFileById(@PathVariable("id") UUID id) {
        return fileService.getFileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
