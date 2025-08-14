package com.rby.demo.sprinai_demo.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Validated and Jakarta Validation: By adding @Validated and validation annotations like @NotEmpty
 * and @NotNull, Spring will validate the properties at startup. This ensures that essential
 * configuration values are not missing or invalid before the application even starts processing
 * requests.
 * <p>
 * ConfigurationProperties: This annotation tells Spring to bind all properties with the prefix test
 * to this class
 * <p>
 * Java Record: The class is defined as a record, making it immutable and concise.
 * <p>
 * AllArgsConstructor is only supported on a class or an enum.
 */
@Validated
@ConfigurationProperties(prefix = "test")
public record TestProps(
    @NotEmpty
    String name,
    String lastName,
    String pwd,
    @NotNull
    Region region
) {

  public record Region(String name, String code) {

  }
}
