package com.sprint.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sprint.ecommerce.dtos.request.ClienteRequestDtos.ClienteCreateRequest;
import com.sprint.ecommerce.dtos.responses.ClienteResponse;
import com.sprint.ecommerce.models.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

     /**
     * Converte o DTO de Criação para a Entidade Cliente.
     * IGNORAMOS o 'usuario' aqui, pois ele será tratado
     * manualmente no Service (para criar o Usuario e setar a senha).
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "usuario", ignore = true) // Ignorado! O Service vai cuidar disso.
    Cliente requestToCliente(ClienteCreateRequest dto);


    /**
     * Converte a Entidade Cliente para o DTO de Resposta.
     * O MapStruct é inteligente e entende "cliente.usuario.id" e "cliente.usuario.email".
     */
    @Mapping(source = "id", target = "idCliente")
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "usuario.email", target = "email")
    ClienteResponse clienteToResponse(Cliente cliente);
}
