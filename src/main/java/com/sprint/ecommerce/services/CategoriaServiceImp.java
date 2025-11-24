package com.sprint.ecommerce.services;

import com.sprint.ecommerce.dtos.request.CategoriaRequestDtos.CategoriaCreateRequest;
import com.sprint.ecommerce.dtos.responses.CategoriaResponse;
import com.sprint.ecommerce.mappers.CategoriaMapper;
import com.sprint.ecommerce.models.Categoria;
import com.sprint.ecommerce.repository.CategoriaRepository;
import com.sprint.ecommerce.services.interfaces.CategoriaService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImp implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImp.class);

    public CategoriaServiceImp(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper){
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Transactional
    @Override
    public CategoriaResponse saveCategoria(CategoriaCreateRequest categoriaRequest) {

        log.info("Iniciando tentativa de salvar Categoria...");

        var novaCategoria = categoriaMapper.requestToCategoria(categoriaRequest);

        log.info("Iniciando salvamento de categoria.");

        var savedCategoria = categoriaRepository.save(novaCategoria);

        log.info("Categoria salva com ID: {}", savedCategoria.getId());

        return categoriaMapper.categoriaToResponse(savedCategoria);
    }

    @Override
    public List<CategoriaResponse> findAll(){

        log.info("Buscando todas as categorias.");
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::categoriaToResponse)
                .collect(Collectors.toList());
    }
}
