package com.sprint.ecommerce.controllers;

import com.sprint.ecommerce.dtos.request.PedidoRequestDtos;
import com.sprint.ecommerce.dtos.request.PedidoRequestDtos.PedidoCreateRequest;
import com.sprint.ecommerce.dtos.responses.PedidoResponse;
import com.sprint.ecommerce.services.interfaces.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/pedidos")
@SecurityRequirement(name = "bearer-key")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){

        this.pedidoService = pedidoService;
    }
    @Operation(summary = "Realiza um novo pedido (Checkout)",
            description = "Realiza um novo pedido com cliente associado. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "pedido realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (ex: Id nulo)",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping
    public ResponseEntity<PedidoResponse> savePedido(@RequestBody @Valid PedidoCreateRequest pedidoRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.savePedido(pedidoRequest));
    }
}
