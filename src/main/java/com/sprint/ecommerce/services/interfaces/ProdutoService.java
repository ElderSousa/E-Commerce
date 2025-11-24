package com.sprint.ecommerce.services.interfaces;

import com.sprint.ecommerce.dtos.request.ProdutoRequestDtos.ProdutoCreateRequest;
import com.sprint.ecommerce.dtos.responses.ProdutoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoService {

    ProdutoResponse saveProduto(ProdutoCreateRequest produtoRequest);
    Page<ProdutoResponse> getAll(Pageable pageable);
}
