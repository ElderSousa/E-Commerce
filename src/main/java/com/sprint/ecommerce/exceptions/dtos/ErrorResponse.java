package com.sprint.ecommerce.exceptions.dtos;

import java.time.LocalDateTime;

public record ErrorResponse(
        String titulo,
        int status, 
        String detalhes,
        LocalDateTime timestamp
) {
  
}
