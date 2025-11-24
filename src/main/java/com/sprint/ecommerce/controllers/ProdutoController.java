package com.sprint.ecommerce.controllers;

import com.sprint.ecommerce.dtos.request.ProdutoRequestDtos.ProdutoCreateRequest;
import com.sprint.ecommerce.dtos.responses.ProdutoResponse;
import com.sprint.ecommerce.services.interfaces.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/produtos")
@SecurityRequirement(name = "bearer-key")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }
     @Operation(summary = "Criando um novo produto", description = "Requer token de autenticação")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "201", description = "Produto criado com sucesso."),
             @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (ex: Id nulo)",
                     content = @Content(schema = @Schema(implementation = Map.class)))
     })
    @PostMapping
    public ResponseEntity<ProdutoResponse> saveProduto(@RequestBody @Valid ProdutoCreateRequest produtoRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.saveProduto(produtoRequest));
    }

    @Operation(summary = "Lista todos os produtos",
            description = "Requer token de autenticação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos devolvida com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> getAll(@PageableDefault(size = 10, sort = "name")Pageable pageable){

        return ResponseEntity.ok(produtoService.getAll(pageable));
    }
}
