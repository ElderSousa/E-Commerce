package com.sprint.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sprint.ecommerce.dtos.request.CategoriaRequestDtos.CategoriaCreateRequest;
import com.sprint.ecommerce.dtos.responses.CategoriaResponse;
import com.sprint.ecommerce.models.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    Categoria requestToCategoria(CategoriaCreateRequest categoriaCreateRequest);

    CategoriaResponse categoriaToResponse(Categoria categoria);
}
