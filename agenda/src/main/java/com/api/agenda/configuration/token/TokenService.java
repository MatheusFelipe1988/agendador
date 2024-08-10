package com.api.agenda.configuration.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TokenService {

    @Value("${auth.token.jwtSecret}")
    private  String secret;

    public String generateJwtTokenForUser(User usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(usuario.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer("agenda-service-api")
                .sign(algorithm);
    }

    public Optional<String> validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return Optional.of(JWT.require(algorithm)
                    .withIssuer("agenda-service-api")
                    .build()
                    .verify(token)
                    .getSubject());
        }catch (JWTVerificationException e){
            return Optional.empty();
        }
    }
}
