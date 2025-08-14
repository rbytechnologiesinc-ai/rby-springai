package com.rby.demo.sprinai_demo.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

/**
 * chat request DTO. * Includes validation, conversation tracking, and model customization options.
 *
 * @param message        The non-blank text content of the user's message.
 * @param conversationId An optional unique identifier to maintain conversational context.
 * @param options        Optional parameters to control the AI model's behavior.
 */
public record ChatRequest(
    @NotBlank(message = "message can't be blank")
    String message,
    UUID conversationId,
    ModelOptions options
) {

  /**
   * Nested record for model-specific parameters.
   *
   * @param model       The specific model to use for the request (e.g., "gpt-4o").
   * @param temperature The creativity/randomness of the response, typically between 0.0 and 1.0.
   **/
  public record ModelOptions(String model, Float temperature) {

  }

}
