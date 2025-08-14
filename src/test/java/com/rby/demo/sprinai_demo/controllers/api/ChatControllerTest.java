package com.rby.demo.sprinai_demo.controllers.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

  @Mock
  private ChatClient chatClient;

  @Mock
  private ChatClient.ChatClientRequestSpec requestSpec;

  @Mock
  private ChatClient.CallResponseSpec responseSpec;

  private ChatController chatController;

  @BeforeEach
  void setUp() {
    chatController = new ChatController(chatClient);
  }

  @Test
  @DisplayName("should return AI response from API")
  void callChatGptShouldReturnAiResponse() {
    // Arrange
    var expectedResponse = "2";
    when(chatClient.prompt()).thenReturn(requestSpec);
    when(requestSpec.user("what is 1 + 1 ")).thenReturn(requestSpec);
    when(requestSpec.call()).thenReturn(responseSpec);
    when(responseSpec.content()).thenReturn(expectedResponse);

    //act
    ResponseEntity<String> response = chatController.callChatGpt();

    //Assert
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(expectedResponse);

    // Verify interactions in order
    verify(chatClient).prompt();
    verify(requestSpec).user("what is 1 + 1 ");
    verify(requestSpec).call();
    verify(responseSpec).content();

  }

}
