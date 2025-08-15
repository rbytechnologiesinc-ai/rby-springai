package com.rby.demo.sprinai_demo.service;

import com.rby.demo.sprinai_demo.ai.prompt.PromptFactory;
import com.rby.demo.sprinai_demo.dto.response.StructuredResponse;
import lombok.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Value
public class ChatService {

  ChatClient chatClient;
  PromptFactory promptFactory;

  public StructuredResponse getActorCharacterInfo(String userQuery, String ragContext) {
    Map<String, Object> model = new HashMap<>();
    model.put("user_query", userQuery);
    model.put("context", ragContext); // RAG data injection

    BeanOutputConverter<StructuredResponse> converter =
        new BeanOutputConverter<>(StructuredResponse.class);

    return promptFactory.executeWithStructuredOutput(
        model,
        "movie_query.st",     // system prompt file
        "acknowledge.st",     // assistant prompt file
        "query.st",           // user prompt file
        converter,
        chatClient
    );
  }
}
