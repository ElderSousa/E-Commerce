package com.sprint.ecommerce.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sprint.ecommerce.repository.UsuarioRepository;
import com.sprint.ecommerce.services.interfaces.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SecurityFilter extends OncePerRequestFilter{

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        var token = this.recoverToken(request).toString();

        if (token != null){
            
            var login = tokenService.validateToken(token);
            if (!login.isEmpty()){
                
                UserDetails user = usuarioRepository.findByEmail(login).orElse(null);
                if (user != null){

                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    //SALVA NO CONTEXTO: Diz ao Spring Security que a requisição está AUTENTICADA.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
     }

    private Object recoverToken(HttpServletRequest request) {
        
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer", "");
    }

    
}
