package com.sprint.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "E-commerce - Sistema de E-commerce",
                description = "API REST para gerenciamento de E-commerce.",
                version = "v1.0.0"
        )
)
@SecurityScheme(
        name = "bearer-key", // Nome usado no @SecurityRequirement dos Controllers
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Insira o token JWT aqui para acessar os endpoints protegidos."
)
public class OpenApiConfig {
}
