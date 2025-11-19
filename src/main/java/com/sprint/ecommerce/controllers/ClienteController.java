package com.sprint.ecommerce.controllers;

import com.sprint.ecommerce.dtos.request.ClienteRequestDtos.ClienteCreateRequest;
import com.sprint.ecommerce.dtos.responses.ClienteResponse;
import com.sprint.ecommerce.services.interfaces.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){

        this.clienteService = clienteService;
    }

    @Operation(summary = "Cria um novo cliente",
        description = "Cria um novo cliente e um novo usuário o associando ao cliente ")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (ex: Id nulo)", 
            content = @Content(schema = @Schema(implementation = Map.class)))
        })
    @PostMapping
    public ResponseEntity<ClienteResponse> salveClient(@RequestBody @Valid ClienteCreateRequest clienteRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(clienteService.saveClient(clienteRequest));
    }

}
