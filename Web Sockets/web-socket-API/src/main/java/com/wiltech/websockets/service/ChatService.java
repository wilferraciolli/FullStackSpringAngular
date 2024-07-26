
package com.wiltech.websockets.service;

import org.springframework.stereotype.Service;

import com.wiltech.websockets.model.ChatMessage;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
public class ChatService {
    private final EmitterProcessor<ChatMessage> chatProcessor = EmitterProcessor.create();
    private final FluxSink<ChatMessage> chatSink = chatProcessor.sink();

    public Flux<ChatMessage> getChatMessages() {
        return chatProcessor.publish().autoConnect();
    }

    public void sendMessage(ChatMessage message) {
        chatSink.next(message);
    }
}
