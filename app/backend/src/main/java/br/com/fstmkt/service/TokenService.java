package br.com.fstmkt.service;

import br.com.fstmkt.dto.response.TokenResponse;
import br.com.fstmkt.entity.Usuario;
import br.com.fstmkt.entity.enums.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private final String SALT = "W+jX#P9o2}40%#J#7e[qGZ)Ps1g6A5@>PVSoDIH+ls87zrd(Z$";
    private final Integer EXPIRES_IN = 599;


    public TokenResponse gerarTokenJwt(Usuario usuario) {
        var algoritmo = gerarAlgoritmo();

        var roles = usuario.getRoles().stream()
                .map(Role::getAuthority)
                .toList();

        var token = getJwtBuilder()
                .withIssuer("teste")
                .withSubject(usuario.getUsername())
                .withExpiresAt(gerarDataExpiracao())
                .withClaim("roles", roles)
                .sign(algoritmo);

        return new TokenResponse(token, EXPIRES_IN);
    }

    public String buscarSubject(String tokenJwt) {
        var algoritmo = gerarAlgoritmo();

        return JWT.require(algoritmo)
                .withIssuer("teste")
                .build()
                .verify(tokenJwt)
                .getSubject();
    }

    private Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusSeconds(EXPIRES_IN).toInstant(ZoneOffset.of("-03:00"));
    }

    private JWTCreator.Builder getJwtBuilder() {
        return JWT.create();
    }

    private Algorithm gerarAlgoritmo() {
        return Algorithm.HMAC256(SALT);
    }
}
