package com.sprint.ecommerce.services.interfaces;

import com.sprint.ecommerce.dtos.request.CategoriaRequestDtos.CategoriaCreateRequest;
import com.sprint.ecommerce.dtos.responses.CategoriaResponse;

import java.util.List;

public interface CategoriaService {
    CategoriaResponse saveCategoria(CategoriaCreateRequest categoriaRequest);
    List<CategoriaResponse> findAll();
}
