package br.com.api.ecoforum_br.service;

import br.com.api.ecoforum_br.model.Usuario;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
;

@Service // Marca como um serviço Spring
public class TokenService {

    @Value("${api.security.token.secret}") // Injeta a chave secreta do application.properties
    private String secret;

    private static final String ISSUER = "API EcoForum ";

    public String gerarToken(Usuario usuario) {
        try {
            // Define o algoritmo de criptografia com a chave secreta
            var algoritmo = Algorithm.HMAC256(secret);
            return com.auth0.jwt.JWT.create()
                    .withIssuer(ISSUER) // Emissor
                    .withSubject(usuario.getLogin()) // "Subject" (assunto) do token: o login do usuário
                    .withClaim("id", usuario.getId()) // Adiciona um "claim" personalizado: o ID do usuário
                    .withExpiresAt(dataExpiracao()) // Define a data de expiração
                    .sign(algoritmo); // Assina o token
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }
    public String getSubject(String tokenJWT) {
        try {
            // Define o algoritmo de verificação com a chave secreta
            var algoritmo = Algorithm.HMAC256(secret);
            // Verifica e decodifica o token, retornando o "subject"
            return com.auth0.jwt.JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

