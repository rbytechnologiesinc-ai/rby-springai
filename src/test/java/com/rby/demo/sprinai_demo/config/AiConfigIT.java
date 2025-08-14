package com.rby.demo.sprinai_demo.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class AiConfigIT {

  @Autowired
  ChatClient chatClient;

  @Test
  @DisplayName("ChatClient bean should be wired")
  void openAiChatClientShouldBeWired(){
    //Assert
    assertThat(chatClient).isNotNull();
  }

}
