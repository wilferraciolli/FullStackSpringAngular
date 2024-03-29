package com.wiltech.llm.ai.chatbot;

import com.wiltech.llm.ai.dtos.ChatRequest;
import com.wiltech.llm.ai.dtos.ChatResponse;
import com.wiltech.llm.ai.services.DataIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatAppService chatService;

    @Autowired
    DataIngestionService dataIngestionService;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ChatResponse processMsg(@RequestBody ChatRequest chatRequest) {
        var aiMessage = chatService.rag(chatRequest);
        var response = ChatResponse.builder().aiMsg(aiMessage).build();

        return response;
    }

    @GetMapping
    public String welcome() {
        return "Welcome to Chat Bot";
    }

    @PostMapping("/setup")
    public void processMsg() {
        dataIngestionService.setupRagChatbot();
    }
}
