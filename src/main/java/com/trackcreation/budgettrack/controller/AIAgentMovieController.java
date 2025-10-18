package com.trackcreation.budgettrack.controller;

import com.trackcreation.budgettrack.response.movie.MovieList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AIAgentMovieController {

    private ChatClient chatClient;

    // simple persistence
    public AIAgentMovieController(ChatClient.Builder builder, ChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(memory).build())
                .build();
    }


    // avec le streaming
    @GetMapping(value = "/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> stream(String query)
    {
        return chatClient
                .prompt()
                .user(query)
                .stream()
                .content();
    }

    // persistence, system message and structure output
    @GetMapping("/chat/cinema")
    public MovieList askLLMMovie(String query) {
        String systemMessage = """
                Vous êtes un spécialiste dans le domaine du cinéma.
                Répond à la question de l'utilisateur à ce propos
                """;
        return chatClient.prompt()
                .system(systemMessage)
                .user(query)
                .call()
                .entity(MovieList.class);
    }
}
