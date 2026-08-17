package com.wiltech.pdfparser.document.parsing;

import java.util.UUID;

public record DocumentParsingRequestedEvent(UUID documentId, byte[] data) {
}
