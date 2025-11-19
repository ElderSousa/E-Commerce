package com.sprint.ecommerce.services.interfaces;

import com.sprint.ecommerce.models.Usuario;

public interface TokenService {

    String generateToken(Usuario usuario);
    String validateToken(String token);
}
