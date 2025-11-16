package com.sprint.ecommerce.dtos.responses;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String email,
        String senha,
        String role,
        LocalDateTime dataCriacao
) {}
