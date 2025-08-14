package com.rby.demo.sprinai_demo.ai.parser;

import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.lang.NonNull;


public class CustomBeanOutputConverter<T> extends BeanOutputConverter<T> {

  //private static final Logger logger = LoggerFactory.getLogger(CustomBeanOutputParser.class);

  /**
   * Constructs a new CustomBeanOutputParser for the given target class.
   *
   * @param targetClass The class of the target bean.
   */
  public CustomBeanOutputConverter(Class<T> targetClass) {
    super(targetClass);
  }

  @Override
  public T convert(@NonNull String text)  {
    //logger.debug("Attempting to parse raw AI response: {}", text);
    String jsonBlock = extractJson(text);
    // logger.debug("Extracted JSON block for parsing: {}", jsonBlock);
    // Delegate the actual parsing to the parent class with the cleaned text
    return super.convert(jsonBlock);


  }

  /**
   * Extracts a JSON object from a string that might contain other text or markdown.
   *
   * @param text The raw text from the AI.
   * @return A string containing only the JSON object.
   */
  private String extractJson(String text) {
    // Find the first opening curly brace
    int firstBrace = text.indexOf('{');
    // Find the last closing curly brace
    int lastBrace = text.lastIndexOf('}');

    if (firstBrace != -1 && lastBrace != -1 && lastBrace > firstBrace) {
      // Extract the substring that contains the JSON object
      return text.substring(firstBrace, lastBrace + 1);
    }

    // If no JSON object is found, return the original text and let the parent parser handle it
//    logger.warn("Could not find a JSON block enclosed in curly braces. Passing raw text to parser.");
    return text;
  }
}

