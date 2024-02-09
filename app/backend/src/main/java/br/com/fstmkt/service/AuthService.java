package br.com.fstmkt.service;

import br.com.fstmkt.dto.request.SignUpRequest;
import br.com.fstmkt.dto.request.TokenRequest;
import br.com.fstmkt.dto.response.MessageResponse;
import br.com.fstmkt.dto.response.TokenResponse;
import br.com.fstmkt.entity.Usuario;
import br.com.fstmkt.entity.enums.Role;
import br.com.fstmkt.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UsuarioRepository usuarioRepository;

    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService,
                       UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    public TokenResponse iniciarProcessoDeAutenticacao(TokenRequest tokenRequest) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword());

        Authentication auth = realizarAutenticacao(authentication);

        Usuario usuario = extrairUsuario(auth);

        return tokenService.gerarTokenJwt(usuario);
    }

//    public TokenResponse realizarAutenticacaoComToken(String token) {
//        Usuario usuario = usuarioService.buscarUsuarioPorToken(token);
//        return tokenService.gerarTokenJwt(usuario);
//    }

    private Authentication realizarAutenticacao(UsernamePasswordAuthenticationToken authentication) {
        return authenticationManager.authenticate(authentication);
    }

    private Usuario extrairUsuario(Authentication auth) {
        return (Usuario) auth.getPrincipal();
    }


    public MessageResponse registrarUsuario(SignUpRequest signUpRequest) {
        if (usuarioRepository.existsByCpf(signUpRequest.getCpf())) {
            return new MessageResponse("Error: Username already existis");
        }

        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return new MessageResponse("Error: Email already in use");
        }

        Usuario usuario = new Usuario(
                signUpRequest.getCpf(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword()
        );

        Set<Role> roles = getRoles(signUpRequest);
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
        return new MessageResponse("Usuário registrado com sucesso");
    }

    private static Set<Role> getRoles(SignUpRequest signUpRequest) {
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new TreeSet<>();

        if (strRoles == null) {
            Role userRole = Role.USUARIO;
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (Objects.equals(role, "Administrados")) {
                    Role adminRole = Role.ADMIN;
                    roles.add(adminRole);
                } else if (Objects.equals(role, "Vendedor")) {
                    Role vendedorRole = Role.VENDEDOR;
                    roles.add(vendedorRole);
                } else if (Objects.equals(role, "Cliente")) {
                    Role clienteRole = Role.CLIENTE;
                    roles.add(clienteRole);
                } else {
                    Role userRole = Role.USUARIO;
                    roles.add(userRole);
                }
            });
        }
        return roles;
    }


    // Para o RefreshToken
//    public TokenResponse atualizarAutenticacao() {
//
//    }
}
