package com.sprint.ecommerce.dtos.request;

import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class CategoriaRequestDtos {
    private CategoriaRequestDtos(){};

    public record CategoriaCreateRequest(
        @NotBlank(message = "O nome não pode ser vazio")
        @Size(min = 3, message = "O nome não pode ter menos que 3 caracteres")
        String nome
    ) {}

    public record CategoriaUpdateRequest(
        Optional<String> nome
    ) {}
}
