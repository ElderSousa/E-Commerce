package com.sprint.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sprint.ecommerce.dtos.request.ProdutoRequestDtos.ProdutoCreateRequest;
import com.sprint.ecommerce.dtos.responses.ProdutoResponse;
import com.sprint.ecommerce.models.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    /**
     * Mapeia o DTO de Criação para a Entidade.
     * A maioria dos campos é mapeada automaticamente (nome, preco, etc.)
     */
    @Mapping(target = "id", ignore = true)        // Ignorado! Gerado pelo banco.
    @Mapping(target = "categoria", ignore = true) // Ignorado! O Service vai buscar pelo categoriaId.
    @Mapping(target = "itens", ignore = true)     // Ignorado! Lado inverso da relação N:N.
    Produto requestToProduto(ProdutoCreateRequest dto);


    /**
     * Mapeia a Entidade para o DTO de Resposta.
     * "Achata" a entidade Categoria para expor apenas o ID e o Nome.
     */
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    ProdutoResponse produtoToResponse(Produto produto);
}
