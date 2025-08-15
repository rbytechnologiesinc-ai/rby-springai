package com.rby.demo.sprinai_demo.ai.prompt;

import com.rby.demo.sprinai_demo.dto.response.StructuredResponse;
import lombok.Value;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * A factory for creating standardized Prompt objects. This class centralizes prompt engineering
 * logic, separating it from business services. It's responsible for constructing prompts with
 * system messages, user queries, and structured output instructions.
 */
@Component
@Value
public class PromptFactoryOld {

  StructuredOutputConverter<StructuredResponse> actorProfileParser;

  /**
   * Creates a prompt to extract a detailed actor profile from a user query.
   *
   * @param query The user's request, e.g., "Tell me about Tom Hanks."
   * @return A Prompt object ready to be sent to the ChatClient.
   */
  @SuppressWarnings("should be renamed")
  public Prompt createStructeredPrompt(String query) {
// The instruction template tells the AI what to do and how to format its response.
    String promptString = """
        You are an expert film-buff assistant.
        Analyze the following user query to extract information about a single, specific actor.
        Your response must be in JSON format. Do not include any introductory text or code block markers.
        
        {format}
        QUERY:
        {query}
        ---
        """;

    PromptTemplate promptTemplate = new PromptTemplate(promptString);
    return promptTemplate.create(Map.of(
        "query", query, "format", actorProfileParser.getFormat()));
  }

}
