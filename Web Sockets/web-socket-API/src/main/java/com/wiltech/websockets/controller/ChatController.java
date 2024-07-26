package com.wiltech.websockets.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.wiltech.websockets.model.ChatMessage;
import com.wiltech.websockets.service.ChatService;

import reactor.core.publisher.Flux;

@Controller
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatService.sendMessage(chatMessage);
        return chatMessage;
    }

    @SubscribeMapping("/chat.getMessages")
    public Flux<ChatMessage> getMessages() {
        return chatService.getChatMessages();
    }
}
