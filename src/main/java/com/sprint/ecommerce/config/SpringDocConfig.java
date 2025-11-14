package com.sprint.ecommerce.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SpringDocConfig {
     @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("E-commerce - Sistema de E-commerce")
                        .description("API REST para gerenciamento de E-commerce.")
                        .version("v1.0.0"));
    }
}
