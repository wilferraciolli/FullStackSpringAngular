package com.wiltech.llm.ai.services;


import com.wiltech.llm.ai.dtos.ChatRequest;
import com.wiltech.llm.ai.models.QuestionAnsweringAgent;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class BasicRagService extends RagService {
    QuestionAnsweringAgent agent;

    @PostConstruct
    public void init() {
        agent = basicQuestionAnsweringAgent();
    }

    /**
     * The answer method of QuestionAnsweringAgent is called which internally
     * calls the invoke method in DefaultAiServices class. This is a basic prompt which may need very good input, on the advanced it will cover more
     */
    public String generateAnswer(ChatRequest chatRequest) {
        if (chatRequest.isNewChatThread()) {
            agent = basicQuestionAnsweringAgent();
        }

        return agent.answer(chatRequest.getUserMsg());
    }

    private QuestionAnsweringAgent basicQuestionAnsweringAgent() {
        ChatLanguageModel chatModel = getChatModel();

        // Chat memory to remember previous interactions
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // The content retriever is responsible for retrieving relevant content
        // from Vector DB based on a user query.
        ContentRetriever contentRetriever = getEmbeddingStoreContentRetriever();

        return AiServices.builder(QuestionAnsweringAgent.class)
                .chatLanguageModel(chatModel)
                .contentRetriever(contentRetriever)
                .chatMemory(chatMemory)
                .build();
    }
}
