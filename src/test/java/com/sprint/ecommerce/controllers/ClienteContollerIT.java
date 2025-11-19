package com.sprint.ecommerce.controllers;
/* 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.ecommerce.dtos.request.ClienteRequestDtos.ClienteCreateRequest;
import com.sprint.ecommerce.dtos.responses.ClienteResponse;
import com.sprint.ecommerce.services.interfaces.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteContollerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    @Test
    void devePermitirCadastroSemLogin() throws Exception{
        //Arrange
        var request = new ClienteCreateRequest(
            "Cliente Teste IT",
                "12345678900",
                LocalDate.of(1990, 1, 1),
                "11988887777",
                "teste-it@email.com",
                "senha123"
        );

        when(clienteService.saveClient(request))
            .thenReturn(new ClienteResponse(1L, "Teste", "123", LocalDate.now(), "111", 1L, "email"));

        //Act & Assert
        mockMvc.perform(post("/api/v1/clientes") // Tenta fazer um POST     
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // Envia o JSON
                .andExpect(status().isCreated()); // ESPERA 201 Created 
                
    }
}*/
