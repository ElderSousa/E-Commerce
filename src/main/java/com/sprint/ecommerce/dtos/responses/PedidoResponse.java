package com.sprint.ecommerce.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponse(
        Long id,
        LocalDateTime dataPedido,
        String status,
        BigDecimal valorTotal
) {}
