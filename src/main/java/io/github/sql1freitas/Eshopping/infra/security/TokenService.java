package io.github.sql1freitas.Eshopping.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.sql1freitas.Eshopping.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j
public class TokenService {
    @Value("${spring.api.security.token.secret}")
    private String secret;

    public String generateToken(Users user){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
           String token =  JWT.create()
                    .withIssuer("eshopping-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao gerar o token", e);
        }
    }

    public String validateToken(String token){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var login= JWT.require(algorithm)
                    .withIssuer("eshopping-api")
                    .build()
                    .verify(token)
                    .getSubject();
            log.info("Validated token for user:{}", login);

            return login;

        }catch (JWTVerificationException e){
            log.warn("Invalid Token: {}", token);
            return "";
        }
    }


    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-03:00"));
    }
}
