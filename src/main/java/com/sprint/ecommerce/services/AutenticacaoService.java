package com.sprint.ecommerce.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sprint.ecommerce.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;
    private static final Logger log = LoggerFactory.getLogger(AutenticacaoService.class);

    public AutenticacaoService(UsuarioRepository usuarioRepository){
       
        this.usuarioRepository =usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Iniciando tentativa autenticação do usuário.");

        return usuarioRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }
}
