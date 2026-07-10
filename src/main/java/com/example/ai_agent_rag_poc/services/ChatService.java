package com.example.ai_agent_rag_poc.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private static final String SYSTEM_PROMPT = """
            Você é um assistente que irá dar respostas precisas consultando dados de um banco de dados vetorial
            """;

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public ChatService(ChatClient chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
    }

    public Flux<String> chat(
            final String command,
            final String sessionId
    ) {
        return this.chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(command)
                .advisors(advisorSpec -> advisorSpec
                        .advisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                        .param("chat_memory_conversation_id", sessionId))
                .stream()
                .content();
    }
}