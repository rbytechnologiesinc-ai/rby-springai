package com.rby.demo.sprinai_demo.service;

import com.rby.demo.sprinai_demo.ai.prompt.PromptFactory;
import com.rby.demo.sprinai_demo.dto.request.ChatRequest;
import com.rby.demo.sprinai_demo.dto.response.ChatResponse;
import com.rby.demo.sprinai_demo.dto.response.StructuredResponse;
import lombok.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Value
public class ChatService {

  ChatClient chatClient;
  PromptFactory promptFactory;
  ChatMemory chatMemory = MessageWindowChatMemory.builder()
      .maxMessages(10)
      .build();

  public StructuredResponse getStructuredResponse(String userQuery,
      String ragContext) {
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

  public ChatResponse getActorCharacterInfo(String userQuery, String ragContext) {
    var converter = new BeanOutputConverter<>(StructuredResponse.class);
    Map<String, Object> model = Map.of(
        "user_query", userQuery,
        "context", ragContext,
        "format", converter.getFormat()
    );

    var prompt = promptFactory.createMultiRolePrompt(
        model, "movie_query.st", "acknowledge.st", "query.st");

    var rawResponse = chatClient.prompt(prompt).call().chatResponse();

    StructuredResponse parsedData =
        converter.convert(rawResponse.getResult().getOutput().getText());

    return new ChatResponse(
        parsedData,
        UUID.randomUUID(), // or real conversation tracking ID
        new ChatResponse.MetaData(
            rawResponse.getMetadata().getModel(),
            new ChatResponse.Usage(
                rawResponse.getMetadata().getUsage().getPromptTokens(),
                rawResponse.getMetadata().getUsage().getCompletionTokens(),
                rawResponse.getMetadata().getUsage().getTotalTokens()
            )
        )
    );
  }

  /**
   * Processes a chat request with dynamic options and conversational memory.
   *
   * @param request    The chat request DTO containing the message, conversation ID, and model
   *                   options.
   * @param ragContext The retrieved RAG context for the user's query.
   * @return A detailed chat response with metadata.
   */
  public ChatResponse getActorCharacterInfo(ChatRequest request, String ragContext) {
    var converter = new BeanOutputConverter<>(StructuredResponse.class);
    Map<String, Object> model = Map.of(
        "user_query", request.message(),
        "context", ragContext,
        "format", converter.getFormat()
    );

    // Apply model options from the request DTO if they exist
    var options = request.options();
    var prompt = promptFactory.createMultiRolePrompt(
        model, "movie_query.st", "acknowledge.st", "query.st");

    // 2. Start the ChatClient fluent API with the created Prompt object.
    var chatClientRequestSpec = chatClient.prompt(prompt)
        .advisors(PromptChatMemoryAdvisor.builder(chatMemory).build());

    // Dynamically set model and temperature if provided in the request
    //also can be set in application.yml
    if (options != null) {
      if (options.model() != null) {
        chatClientRequestSpec.options(ChatOptions.builder().model(options.model()).build());
      }
      if (options.temperature() != null) {
        chatClientRequestSpec.options(ChatOptions.builder().temperature(
            Double.valueOf(options.temperature())).build());
      }
    }

    var rawResponse = chatClientRequestSpec.call().chatResponse();

    StructuredResponse parsedData = converter.convert(
        rawResponse.getResult().getOutput().getText());

    return new ChatResponse(
        parsedData,
        request.conversationId() != null ? request.conversationId() : null,
        new ChatResponse.MetaData(
            rawResponse.getMetadata().getModel(),
            new ChatResponse.Usage(
                rawResponse.getMetadata().getUsage().getPromptTokens(),
                rawResponse.getMetadata().getUsage().getCompletionTokens(),
                rawResponse.getMetadata().getUsage().getTotalTokens()
            )
        )
    );
  }

}
