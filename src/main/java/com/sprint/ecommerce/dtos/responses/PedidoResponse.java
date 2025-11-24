package com.sprint.ecommerce.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        Long clienteId,
        String status,
        BigDecimal valorTotal,
        LocalDateTime dataPedido,
        List<ItemPedidoResponse> itens
) {
    public record ItemPedidoResponse(
            String nomeProduto,
            Integer quantidade,
            BigDecimal precoUnitario
    ) {}
}
