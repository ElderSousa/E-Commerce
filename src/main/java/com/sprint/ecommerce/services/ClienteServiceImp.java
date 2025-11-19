package com.sprint.ecommerce.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sprint.ecommerce.dtos.request.ClienteRequestDtos.ClienteCreateRequest;
import com.sprint.ecommerce.dtos.responses.ClienteResponse;
import com.sprint.ecommerce.models.enums.Role;
import com.sprint.ecommerce.exceptions.BusinessException;
import com.sprint.ecommerce.mappers.ClienteMapper;
import com.sprint.ecommerce.models.Usuario;
import com.sprint.ecommerce.repository.ClienteRepository;
import com.sprint.ecommerce.repository.UsuarioRepository;
import com.sprint.ecommerce.services.interfaces.ClienteService;

import jakarta.transaction.Transactional;

@Service
public class ClienteServiceImp implements ClienteService{

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClienteMapper clienteMapper;
    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImp.class);
    
    public ClienteServiceImp(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.clienteMapper = clienteMapper;
    }

    @Transactional
    @Override
    public ClienteResponse saveClient(ClienteCreateRequest clienteRequest){
        
        log.info("Iniciando tentativa de salvar cliente...");

        if (usuarioRepository.findByEmail(clienteRequest.email()).isPresent()){
            throw new BusinessException("Existe usuário cadastrado com email: " + clienteRequest.email() + "em nossa base de dados.");
        }

        if (clienteRepository.findByCpf(clienteRequest.cpf()).isPresent()){
            throw new BusinessException("Existe cliente cadastrado com cpf: " + clienteRequest.cpf() + "em nossa base de dados.");
        }

       log.info("Validacões de email e cpf");

       var novoUsuario = Usuario.builder()
            .email(clienteRequest.email())
            .senha(passwordEncoder.encode(clienteRequest.senha()))
            .role(Role.USER)
            .build();
        
        var savedUsuario = usuarioRepository.save(novoUsuario);
        log.info("Usuário salvo com o ID: {}", savedUsuario.getId());

        var novoCliente = clienteMapper.requestToCliente(clienteRequest);

        novoCliente.setUsuario(savedUsuario);

        savedUsuario.setCliente(novoCliente);

        log.info("Iniciando salvamento de cliente e usuário associado.");

        var savedCliente = clienteRepository.save(novoCliente);

        log.info("Cliente salvo como o ID: {}", savedCliente.getId());

        return clienteMapper.clienteToResponse(savedCliente);
    }
}
