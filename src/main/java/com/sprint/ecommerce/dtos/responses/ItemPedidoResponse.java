package com.sprint.ecommerce.dtos.responses;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long id, 
        Integer quantidade,
        BigDecimal precoUnitario        
) {}
