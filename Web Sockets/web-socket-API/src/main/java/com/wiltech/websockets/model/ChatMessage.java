package com.wiltech.websockets.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private String source;

    public ChatMessage() {
    }

    public ChatMessage(final String message, final String source) {
        this.message = message;
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public String getSource() {
        return source;
    }
}
