package br.com.fiap.spaceguard.security.service;

import br.com.fiap.spaceguard.usuario.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.expiration-hours}")
    private Long expirationHours;

    public String gerarToken(Usuario usuario) {
        var algoritmo = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(usuario.getLogin())
                .withClaim("perfil", usuario.getPerfil().name())
                .withExpiresAt(gerarDataExpiracao())
                .sign(algoritmo);
    }

    public String getSubject(String tokenJwt) {
        var algoritmo = Algorithm.HMAC256(secret);

        return JWT.require(algoritmo)
                .withIssuer(issuer)
                .build()
                .verify(tokenJwt)
                .getSubject();
    }

    private Instant gerarDataExpiracao() {
        return Instant.now().plus(Duration.ofHours(expirationHours));
    }
}