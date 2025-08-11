package com.rby.demo.sprinai_demo.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpringAiController {

    private final ChatClient chatClient;

    public SpringAiController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

//    @GetMapping("/ai")
//    String generation() {
//        return this.chatClient.prompt()
//                .user("what is 1 + 1 ")
//                .call()
//                .content();
//    }

    @Operation(summary = "call chatgpt")
    @ApiResponse(responseCode = "200", description = "AI response")
    @GetMapping("/ai")
    public ResponseEntity<String> getAllPatients() {

        String response= this.chatClient.prompt()
                .user("what is 1 + 1 ")
                .call()
                .content();
        return ResponseEntity.ok(response);
    }

}
