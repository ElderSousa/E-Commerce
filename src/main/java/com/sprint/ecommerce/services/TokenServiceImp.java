package com.sprint.ecommerce.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sprint.ecommerce.models.Usuario;
import com.sprint.ecommerce.services.interfaces.TokenService;

@Service
public class TokenServiceImp implements TokenService{

    private String secret = "minha-chave-secreta-sprint-ecommerce";

    @Override
    public String generateToken(Usuario usuario) {
       try{

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("sprint-ecommerce-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
       }catch(JWTCreationException ex){

            throw new RuntimeException("Erro ao gerar o token: ", ex);
       }
    }

    @Override
    public String validateToken(String token) {
        try{

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
            .withIssuer("sprint-ecommerce-api")
            .build()
            .verify(token)
            .getSubject();

        }catch(JWTVerificationException ex){
            return "";
        }
    }

    private Instant genExpirationDate(){

        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
