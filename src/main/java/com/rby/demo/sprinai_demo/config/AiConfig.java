package com.rby.demo.sprinai_demo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Main class to config our AI Models
 */
@Configuration
public class AiConfig {

  /**
   * openAiChatClient
   *
   * @param builder chatclientBuilder
   * @return ChatClient objet
   */
  @Primary
  @Bean
  public ChatClient openAiChatClient(ChatClient.Builder builder) {
    return builder.build();
  }

//    // Similarly for embeddings
//    @Bean
//    public EmbeddingClient embeddingClient(EmbeddingClient.Builder
//                                                   builder) {
//        return builder.build();
//    }

}
