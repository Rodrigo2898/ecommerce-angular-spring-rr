package br.com.fstmkt.config.security;

import br.com.fstmkt.entity.Usuario;
import br.com.fstmkt.service.TokenService;
import br.com.fstmkt.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public SecurityFilter(TokenService tokenService, UsuarioService usuarioService) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var tokenOptional = buscarToken(request);

        tokenOptional.ifPresent((token) -> {
            var username = tokenService.buscarSubject(token);
            var usuario = usuarioService.buscarUsuarioPorUsername(username);
            realizarAutenticacao(usuario);
        });
        filterChain.doFilter(request, response);
    }

    private Optional<String> buscarToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");

        if (Objects.nonNull(token) && token.startsWith("Bearer")) {
            var tokenJwt = token.replace("Bearer", "");

            return Optional.of(tokenJwt);
        }
        return Optional.empty();
    }

    private void realizarAutenticacao(Usuario usuario) {
        var authentication = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getRoles());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
