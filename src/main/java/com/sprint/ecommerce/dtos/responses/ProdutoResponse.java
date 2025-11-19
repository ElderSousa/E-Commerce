package com.sprint.ecommerce.dtos.responses;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        Long categoriaId,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        String categoriaNome
) {}
