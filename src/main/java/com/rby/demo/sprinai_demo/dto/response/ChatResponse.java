package com.rby.demo.sprinai_demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Chat response DTO. Includes the AI-generated content, conversation tracking ID, and detailed
 * metadata.
 *
 * @param content        content The primary text content of the AI's reply.
 * @param conversationId conversationId The unique identifier for the conversation, echoed from the
 *                       request.
 * @param metaData       metadata Contains usage information (like token counts) and other metadata
 *                       from the provider.
 */
public record ChatResponse(
    String content,
    UUID conversationId,
    MetaData metaData
) {

  /**
   * Nested record to structure metadata from the AI provider.
   *
   * @param model The model that generated the response.
   * @param usage Details on token consumption.
   */
  public record MetaData(
      String model,
      Usage usage
  ) {

  }

  /**
   * Nested record for token usage details. Field names are mapped to common provider outputs using
   *
   * @param promptTokens     Tokens used in the prompt.
   * @param generationTokens Tokens used in the generated response.
   * @param totalTokens      Total tokens consumed for the API call.
   * @JsonProperty.
   */
  public record Usage(
      @JsonProperty("prompt_tokens") Long promptTokens,
      @JsonProperty("generation_tokens") Long generationTokens,
      @JsonProperty("total_tokens") Long totalTokens
  ) {

  }
}
