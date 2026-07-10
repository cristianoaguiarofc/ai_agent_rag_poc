package com.example.ai_agent_rag_poc.controllers;

import com.example.ai_agent_rag_poc.services.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public Flux<String> chat(
            @RequestParam(value = "command") final String command,
            @RequestParam(value = "sessionId", required = false) String sessionId
    ) {
        return this.chatService.chat(command, sessionId);
    }

}
