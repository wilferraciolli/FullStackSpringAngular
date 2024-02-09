
package com.wiltech.llm.ai.chatbot;

import com.wiltech.llm.ai.dtos.ChatRequest;
import com.wiltech.llm.ai.services.AdvancedRagService;
import com.wiltech.llm.ai.services.BasicRagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatAppService {
    @Autowired
    BasicRagService basicRagService;

    @Autowired
    AdvancedRagService advancedRagService;

    public String rag(ChatRequest chatRequest) {
        return advancedRagService.generateAnswer(chatRequest);
    }
}
