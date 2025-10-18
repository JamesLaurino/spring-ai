package com.trackcreation.budgettrack.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AIAgentController {

    private ChatClient chatClient;

    // simple persistence
    public AIAgentController(ChatClient.Builder builder, ChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(memory).build())
                .build();
    }

    // Simple asking
    @GetMapping("/chat")
    public String askLLM(String query) {
        return chatClient.prompt()
                .user(query).call().content();
    }

    // Asking with few shot prompt
    @GetMapping("/chat/system")
    public String askLLMSystemMessage(String query) {
        List<Message> exemple = List.of(
                new UserMessage("6+4"),
                new AssistantMessage("Le résultat est : 10")
        );
        return chatClient.prompt()
                .system("Répond toujours en majuscule")
                .messages(exemple)
                .user(query).call().content();
    }

}
