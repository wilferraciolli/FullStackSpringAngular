package com.wiltech.llm.ai.dtos;

import lombok.Data;

@Data
public class ChatRequest {
    private String userMsg;
    private boolean newChatThread;
}
