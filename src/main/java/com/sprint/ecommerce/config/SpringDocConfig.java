package com.sprint.ecommerce.config;

import org.springframework.context.annotation.Bean;

public class SpringDocConfig {
     @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("BookStore - Sistema de gerenciamento de livros")
                        .description("API REST para gerenciamento livros.")
                        .version("v1.0.0"));
    }
}
