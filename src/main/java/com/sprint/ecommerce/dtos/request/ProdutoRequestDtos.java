package com.sprint.ecommerce.dtos.request;

import java.math.BigDecimal;
import java.util.Optional;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class ProdutoRequestDtos {

    private ProdutoRequestDtos(){};

    public record ProdutoCreateRequest(
        @NotBlank(message = "O nome não pode está em branco")
        @Size(min = 3, message = "O nome não pode ter menos que 3 caracteres")
        String nome,
        String descricao,
        @NotNull(message = "O preco não pode ser nulo")
        @DecimalMin(value = "0.01", message = "O preço deve ser positivo")
        BigDecimal preco,
        @NotNull(message = "O estoque não pode ser nulo")
        @Min(value = 0, message = "O estoque não pode ser negativo")
        Integer estoque,
        @NotNull(message = "O id da categoria não pode ser nulo")
        Long CategoriaId
    ) {}

    public record ProdutoUpdateRequest(
        Optional<String> nome,
        Optional<String> descricao,
        Optional<BigDecimal> preco,
        Optional<Integer> estoque,
        Optional<Long> CategoriaId
    ) {}
}
