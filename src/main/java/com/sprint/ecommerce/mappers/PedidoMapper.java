package com.sprint.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sprint.ecommerce.dtos.request.PedidoRequestDtos.PedidoCreateRequest;
import com.sprint.ecommerce.models.Pedido;

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
     * O mapper de resposta será mais complexo (Dia 3),
     * pois precisará converter o Set<ItemPedido> (Entidade)
     * para uma List<ItemResponse> (DTO).
     */
    // @Mapping(source = "cliente.id", target = "clienteId")
    // @Mapping(source = "itens", target = "itensResponse") // (Método customizado aqui)
    // PedidoResponseDTO pedidoToResponse(Pedido pedido);
}
