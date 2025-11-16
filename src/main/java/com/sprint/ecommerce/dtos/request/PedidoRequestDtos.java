package com.sprint.ecommerce.dtos.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public final class PedidoRequestDtos {
    private PedidoRequestDtos(){};

    public record ItemDoPedidoRequest(
        
        @NotNull(message = "O ID do produto não pode ser nulo")
        Long produtoId,

        @NotNull(message = "A quantidade não pode ser nula")
        @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
        Integer quantidade
    ) {}


    /**
     * DTO para criar um novo Pedido.
     * Note que ele contém a lista de "filhos" (o lado N).
     */
    public record PedidoCreateRequest(

        // (No mundo real, o clienteId viria do usuário logado,
        // mas para o DTO, vamos supor que ele precise ser passado
        // se o admin estiver criando um pedido para um cliente)
        @NotNull(message = "O ID do cliente não pode ser nulo")
        Long clienteId,

        @NotEmpty(message = "O pedido deve ter pelo menos um item")
        @Valid // Assegura que os DTOs internos (ItemDoPedidoRequest) também sejam validados
        List<ItemDoPedidoRequest> itens
    ) {}
}
