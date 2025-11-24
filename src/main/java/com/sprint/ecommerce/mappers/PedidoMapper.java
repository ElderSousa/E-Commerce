package com.sprint.ecommerce.mappers;

import com.sprint.ecommerce.dtos.responses.PedidoResponse;
import com.sprint.ecommerce.models.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sprint.ecommerce.dtos.request.PedidoRequestDtos.PedidoCreateRequest;
import com.sprint.ecommerce.models.Pedido;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

     /**
     * Este mapper é muito simples. Ele só cria o objeto 'Pedido'
     * vazio. O Service é quem vai preencher TUDO.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)     // Ignorado! O Service vai buscar pelo clienteId.
    @Mapping(target = "itens", ignore = true)       // Ignorado! O Service vai criar a partir da lista de DTOs.
    @Mapping(target = "dataPedido", ignore = true)  // Ignorado! O @PrePersist vai setar.
    @Mapping(target = "status", ignore = true)      // Ignorado! O Service vai setar como PENDENTE.
    @Mapping(target = "valorTotal", ignore = true)  // Ignorado! O Service vai calcular.
    Pedido requestToPedido(PedidoCreateRequest dto);


    /**
     * Mapeia o Pedido (Pai).
     * O MapStruct detecta a lista 'itens' e usa o método 'itemToResponse' automaticamente para cada elemento.
     */
    @Mapping(source = "cliente.id", target = "clienteId") // Pega o ID de dentro do objeto Cliente
    @Mapping(source = "itens", target = "itens") // Explicito para clareza (Set -> List)
    PedidoResponse pedidoToResponse(Pedido pedido);

    /**
     * Método auxiliar para mapear UM ItemPedido para o DTO interno ItemPedidoResponse.
     * O MapStruct usa isso num loop para converter a lista.
     */
    @Mapping(source = "produto.nome", target = "nomeProduto") // Navega ItemPedido -> Produto -> Nome
    PedidoResponse.ItemPedidoResponse itemToResponse(ItemPedido item);
}
