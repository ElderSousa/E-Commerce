package com.sprint.ecommerce.dtos.request;

import java.time.LocalDate;
import java.util.Optional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class ClienteRequestDtos {
    private ClienteRequestDtos(){};

    public record ClienteCreateRequest(
        @NotBlank(message = "O nome não pode ser vazio")
        @Size(min = 3, message = "O nome não pode ter menos de 3 caracteres")
        String nomeCompleto,
        @NotBlank(message = "O CPF não pode ser vazio")
        @org.hibernate.validator.constraints.br.CPF(message = "CPF inválido")
        String cpf,
        @NotNull(message = "A data de nascimento não pode ser nula")
        LocalDate dataDeNascimento,
        @NotNull(message = "O telefone não pode está vazio")
        @Pattern(regexp = "[0-9]{10,11}", message = "O telefone deve conter apenas números, entre 10 e 11 dígitos (DDD + Número)")
        String telefone,

        @NotBlank(message = "O email não pode está vazio")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "A senha não pode está vazia")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String senha
    ) {}

    public record ClienteUpdateResques(
        Optional<String> nomeCompleto,
        Optional<LocalDate> dataDeNascimento,
        @Pattern(regexp = "[0-9]{10-11}", message = "O telefone deve conter apenas números, entre 10 e 11 dígitos (DDD + Número)")
        String telefone
    ) {}

    
}