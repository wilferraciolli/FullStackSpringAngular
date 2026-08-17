package com.wiltech.pdfparser.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateDocumentRequest(
        @NotBlank String name,
        @NotBlank String type,
        @PositiveOrZero long size) {
}
