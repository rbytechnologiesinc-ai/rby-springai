package com.rby.demo.sprinai_demo.controllers.api;


import com.rby.demo.sprinai_demo.config.TestProps;
import com.rby.demo.sprinai_demo.dto.request.ChatRequest;
import com.rby.demo.sprinai_demo.dto.response.ChatResponse;
import com.rby.demo.sprinai_demo.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Value
public class ChatController {

  ChatClient chatClient;
  TestProps testProps;
  ChatService chatService;


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


  @Operation(summary = "get actor")
  @ApiResponse(responseCode = "200", description = "very good movies")
  @GetMapping("/actor-character")
  public ResponseEntity<ChatResponse> getActorCharacterInfo(
      @RequestParam String query,
      @RequestParam(required = false, defaultValue = "") String context
  ) {
    ChatResponse result = chatService.getActorCharacterInfo(query, context);

    if (result == null) {
      return ResponseEntity.notFound().build(); // return 404 if AI gave no match
    }

    return ResponseEntity.ok(result); // return 200 with body
  }

  @Operation(summary = "get actor with chat request")
  @ApiResponse(responseCode = "200", description = "very good movies chat request")
  @PostMapping("/actor-character_request")
  public ResponseEntity<ChatResponse> getActorCharacterInfoWithChatRequest(
      @Valid @RequestBody ChatRequest chatRequest,
      @RequestParam(required = false, defaultValue = "") String context
  ) {

    ChatResponse result = chatService.getActorCharacterInfo(chatRequest, context);

    if (result == null) {
      return ResponseEntity.notFound().build(); // return 404 if AI gave no match
    }

    return ResponseEntity.ok(result); // return 200 with body
  }


}
