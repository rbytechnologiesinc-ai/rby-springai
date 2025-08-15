package com.rby.demo.sprinai_demo.config;

import com.rby.demo.sprinai_demo.dto.response.StructuredResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;
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

  @Bean
  public StructuredOutputConverter<StructuredResponse> structuredResponseConverter() {
    return new BeanOutputConverter<>(StructuredResponse.class);
  }

//    // Similarly for embeddings
//    @Bean
//    public EmbeddingClient embeddingClient(EmbeddingClient.Builder
//                                                   builder) {
//        return builder.build();
//    }

//  // 1. Define the ChatMemoryRepository bean
//  @Bean
//  public ChatMemoryRepository chatMemoryRepository() {
//    return new InMemoryChatMemoryRepository();
//  }
//
//  // 2. Define the MessageWindowChatMemory bean, which uses the repository.
//  @Bean
//  public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
//    // The constructor is a factory method that uses the repository.
//    // The window size is 30 by default. You can also define it here.
//    return new MessageWindowChatMemory(chatMemoryRepository);
//  }
}
