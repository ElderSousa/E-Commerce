package com.sprint.ecommerce.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public final class AuthenticationRequestDtos {

    private AuthenticationRequestDtos(){};

    public record LoginRequest(
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Formato de email inválido.")
        String email,

        @NotBlank(message = "Senha obrigatória.")
        String senha
    ) {}
}
