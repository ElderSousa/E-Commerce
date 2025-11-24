package com.sprint.ecommerce.services;

import com.sprint.ecommerce.dtos.request.ProdutoRequestDtos.ProdutoCreateRequest;
import com.sprint.ecommerce.dtos.responses.ProdutoResponse;
import com.sprint.ecommerce.exceptions.dtos.ResourceNotFoundException;
import com.sprint.ecommerce.mappers.ProdutoMapper;
import com.sprint.ecommerce.models.Produto;
import com.sprint.ecommerce.repository.CategoriaRepository;
import com.sprint.ecommerce.repository.ProdutoRepository;
import com.sprint.ecommerce.services.interfaces.ProdutoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImp implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoMapper produtoMapper;
    private static final Logger log = LoggerFactory.getLogger(Produto.class);

    public ProdutoServiceImp(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.produtoMapper = produtoMapper;
    }

    @Transactional
    @Override
    public ProdutoResponse saveProduto(ProdutoCreateRequest produtoRequest){

        log.info("Iniciando tentativa de salvar um produto...");

        var categoria = categoriaRepository.findById(produtoRequest.CategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "id ", produtoRequest.CategoriaId()));

        var produto = produtoMapper.requestToProduto(produtoRequest);
        produto.setCategoria(categoria);

        log.info("Tentativa de salvamento de produto");

        var savedProduto = produtoRepository.save(produto);

        log.info("Produto com o ID {} salvo com sucesso.", savedProduto.getId());

        return produtoMapper.produtoToResponse(savedProduto);
    }

    @Override
    public Page<ProdutoResponse> getAll(Pageable pageable){
        return produtoRepository.findAll(pageable)
                .map(produtoMapper::produtoToResponse);
    }
}
