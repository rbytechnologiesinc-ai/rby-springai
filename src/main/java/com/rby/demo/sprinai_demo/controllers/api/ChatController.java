package com.rby.demo.sprinai_demo.controllers.api;


import com.rby.demo.sprinai_demo.config.TestProps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Value
public class ChatController {

  ChatClient chatClient;
  TestProps testProps;

  @Operation(summary = "get props")
  @ApiResponse(responseCode = "200", description = "good")
  @GetMapping("/props")
  public ResponseEntity<String> getAppPorps() {

    return ResponseEntity.ok(testProps.toString());
  }

  @Operation(summary = "call chatgpt")
  @ApiResponse(responseCode = "200", description = "AI response")
  @GetMapping("/ai")
  public ResponseEntity<String> callChatGpt() {

    String response = this.chatClient.prompt()
        .user("what is 1 + 1 ")
        .call()
        .content();
    return ResponseEntity.ok(response);
  }

}
