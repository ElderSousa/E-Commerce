package com.sprint.ecommerce.dtos.responses;

import java.time.LocalDate;

public record ClienteResponse(
        Long id,
        String nomeCompleto,
        String cpf,
        LocalDate dataDeNascimento,
        String telefone,
        Long usuarioId,
        String email
) {}
