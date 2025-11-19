package com.sprint.ecommerce.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sprint.ecommerce.dtos.request.ClienteRequestDtos.ClienteCreateRequest;
import com.sprint.ecommerce.dtos.responses.ClienteResponse;
import com.sprint.ecommerce.mappers.ClienteMapper;
import com.sprint.ecommerce.models.Cliente;
import com.sprint.ecommerce.models.Usuario;
import com.sprint.ecommerce.models.enums.Role;
import com.sprint.ecommerce.repository.ClienteRepository;
import com.sprint.ecommerce.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImp clienteServiceImp;
    
    private ClienteCreateRequest clienteCreateRequest;
    private Usuario usuarioSalvo;
    private Cliente clienteSalvo;
    private ClienteResponse clienteResponse;

    @BeforeEach
    void setup(){

        clienteCreateRequest = new ClienteCreateRequest(
            "Cliente Teste",
            "12345678900", // (Em um teste real, usaríamos um CPF válido)
            LocalDate.of(1990, 1, 1),
            "11988887777",
            "teste@email.com",
            "senha123"
        );
         usuarioSalvo = Usuario.builder()
                .id(1L)
                .email("teste@email.com")
                .senha("senhaCriptografada123")
                .role(Role.USER)
                .build();
        
        clienteSalvo = Cliente.builder()
                .id(1L)
                .nomeCompleto("Cliente Teste")
                .cpf("12345678900")
                .usuario(usuarioSalvo)
                .build();
        
       clienteResponse = new ClienteResponse(
                1L,
                "Cliente Teste",
                "12345678900",
                LocalDate.of(1990, 1, 1),
                "11988887777",
                1L,
                "teste@email.com"
        );
    }

    @Test
    void deveCriarClienteComSucesso(){
        //Arrange
        when(usuarioRepository.findByEmail(clienteCreateRequest.email()))
            .thenReturn(Optional.empty());
        when(clienteRepository.findByCpf(clienteCreateRequest.cpf()))
            .thenReturn(Optional.empty());
        when(clienteMapper.requestToCliente(any(ClienteCreateRequest.class)))
            .thenReturn(clienteSalvo);
        when(passwordEncoder.encode("senha123"))
            .thenReturn("senhaCriptografada123");
        when(clienteRepository.save(any(Cliente.class)))
            .thenReturn(clienteSalvo);
        when(clienteMapper.clienteToResponse(clienteSalvo))
            .thenReturn(clienteResponse);

        //ACT
        var result = clienteServiceImp.saveClient(clienteCreateRequest);

        //ASSERT
        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo(clienteResponse.email());
        assertThat(result.id()).isEqualTo(clienteResponse.id());
    }
}
