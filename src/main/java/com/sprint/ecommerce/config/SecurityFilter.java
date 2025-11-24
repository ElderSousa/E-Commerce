package com.sprint.ecommerce.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sprint.ecommerce.repository.UsuarioRepository;
import com.sprint.ecommerce.services.interfaces.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.recoverToken(request);

        if (token != null) {
            // Mudei para INFO para vermos no console
            log.info("🔎 Filtro: Token detectado. Tentando validar...");

            String login = tokenService.validateToken(token);

            if (login != null && !login.isEmpty()) {
                log.info("✅ Filtro: Token válido! Usuário no token: {}", login);

                UserDetails user = usuarioRepository.findByEmail(login).orElse(null);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("🔓 Filtro: Usuário {} autenticado com sucesso!", user.getUsername());
                } else {
                    log.error("❌ Filtro: Usuário do token ({}) NÃO encontrado no banco!", login);
                }
            } else {
                log.warn("⚠️ Filtro: Token inválido ou expirado (Assinatura não bate).");
            }
        } else {
            // Ignora logs para endpoints públicos para não poluir
            if (!request.getRequestURI().contains("/auth/login") && !request.getRequestURI().contains("/swagger") && !request.getRequestURI().contains("/v3/api-docs")) {
                log.warn("🚫 Filtro: Nenhum token encontrado no Header Authorization para a rota: {}", request.getRequestURI());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;

        // Log para verificar o formato exato do header
        log.debug("Header Authorization recebido: {}", authHeader);

        // Remove "Bearer " com espaço, mas trata caso venha sem espaço ou duplicado
        return authHeader.replace("Bearer ", "").trim();
    }
}
