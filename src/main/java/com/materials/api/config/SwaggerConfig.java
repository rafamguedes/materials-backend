package com.materials.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Materials API Documentation")
                .version("1.0.0")
                .description(
                    "API documentation for the Materials application. This API provides "
                        + "endpoints for managing data, including creation, retrieval, "
                        + "updating, and deletion records.")
                .license(new License().name("Apache 2.0").url("https://springdoc.org")));
  }
}
