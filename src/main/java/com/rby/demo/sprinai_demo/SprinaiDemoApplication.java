package com.rby.demo.sprinai_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SprinaiDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SprinaiDemoApplication.class, args);
  }

}
