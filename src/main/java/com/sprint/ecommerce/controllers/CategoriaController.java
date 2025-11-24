package com.sprint.ecommerce.controllers;

import com.sprint.ecommerce.dtos.request.CategoriaRequestDtos.CategoriaCreateRequest;
import com.sprint.ecommerce.dtos.responses.CategoriaResponse;
import com.sprint.ecommerce.services.interfaces.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categorias")
@SecurityRequirement(name = "bearer-key")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){

        this.categoriaService = categoriaService;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova categoria", description = "Requer token de autenticação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (ex: Id nulo)",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<CategoriaResponse> saveCategoria(@RequestBody @Valid CategoriaCreateRequest categoriaCreateRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.saveCategoria(categoriaCreateRequest));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> findAll(){

        return ResponseEntity.ok(categoriaService.findAll());
    }
}
