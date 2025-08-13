package com.rby.demo.sprinai_demo.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ChatController {

  private final ChatClient chatClient;


  @Operation(summary = "call chatgpt")
  @ApiResponse(responseCode = "200", description = "AI response")
  @GetMapping("/ai")
  public ResponseEntity<String> getAllPatients() {
    String response = this.chatClient.prompt()
        .user("what is 1 + 1 ")
        .call()
        .content();
    return ResponseEntity.ok(response);
  }

}
