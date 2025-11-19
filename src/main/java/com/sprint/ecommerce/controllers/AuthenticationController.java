package com.sprint.ecommerce.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.ecommerce.dtos.request.AuthenticationRequestDtos.LoginRequest;
import com.sprint.ecommerce.dtos.responses.LoginResponse;
import com.sprint.ecommerce.models.Usuario;
import com.sprint.ecommerce.services.interfaces.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService){

        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Realiza o login do usuário", 
               description = "Recebe email e senha, valida as credenciais e retorna um token JWT (Bearer Token).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso. Token retornado."),
        @ApiResponse(responseCode = "403", description = "Credenciais inválidas (Email ou senha incorretos).",
            content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login( @RequestBody @Valid LoginRequest loginRequest){

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha());

        var auth = authenticationManager.authenticate(usernamePassword);

        var usuario = (Usuario)auth.getPrincipal();

        var token = tokenService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
