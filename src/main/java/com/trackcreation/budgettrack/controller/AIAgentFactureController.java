package com.trackcreation.budgettrack.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AIAgentFactureController {
    private ChatClient chatClient;

    // simple persistence
    public AIAgentFactureController(ChatClient.Builder builder, ChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(memory).build())
                .build();
    }

    @PostMapping(value = "/askImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String askLLMImage(@RequestParam(name = "file")MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();

        String systemMessage = """
                Tu es un sépcialiste de la facturation.
                Sur base de l'image que le user te donne récupère le montant de la facture
                """;

        return chatClient.prompt()
                .system("Tu es un sépcialiste de la facturation. Sur base de l'image que le user te donne ")
                .user(u -> u.text("Montant de la facture")
                        .media(MediaType.IMAGE_JPEG,new ByteArrayResource(bytes)))
                .call().content();
    }
}
