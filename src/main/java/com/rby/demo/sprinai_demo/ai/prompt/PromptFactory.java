package com.rby.demo.sprinai_demo.ai.prompt;

import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.prompt.*;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *
 */
@Component
public class PromptFactory {

  /**
   *
   * @param relativePath prompts file
   * @return PromptTemplate
   */
  public PromptTemplate loadTemplate(String relativePath) {
    String templateString = loadFromClasspath("prompts/" + relativePath);
    return new PromptTemplate(templateString);
  }

  /**
   *
   * @param model model
   * @param systemFile systemFile
   * @param assistantFile assistantFile
   * @param userFile userFile
   * @return Prompt
   */
  public Prompt createMultiRolePrompt(
      Map<String, Object> model,
      String systemFile,
      String assistantFile,
      String userFile
  ) {
    List<Message> messages = new ArrayList<>();

    // System role
    messages.add(new SystemPromptTemplate(loadFromClasspath("prompts/system/" + systemFile))
        .createMessage(model));

    // Assistant role
    messages.add(new AssistantMessage(
        loadTemplate("assistant/" + assistantFile).render(model)
    ));

    // User role
    messages.add(new UserMessage(
        loadTemplate("user/" + userFile).render(model)
    ));

    return new Prompt(messages);
  }

  public <T> T executeWithStructuredOutput(
      Map<String, Object> model,
      String systemFile,
      String assistantFile,
      String userFile,
      BeanOutputConverter<T> outputConverter,
      org.springframework.ai.chat.client.ChatClient chatClient
  ) {
    model.put("format", outputConverter.getFormat());

    Prompt prompt = createMultiRolePrompt(model, systemFile, assistantFile, userFile);

    String response = chatClient.prompt(prompt).call()
        .chatResponse()
        .getResult()
        .getOutput()
       // .getContent();
        .getText();

    return outputConverter.convert(response);
  }

  private String loadFromClasspath(String path) {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
      if (is == null) throw new IllegalArgumentException("Template not found: " + path);
      return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Error loading template: " + path, e);
    }
  }
}
