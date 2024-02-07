package br.com.fstmkt.service;

import br.com.fstmkt.dto.request.TokenRequest;
import br.com.fstmkt.dto.response.RecaptchaVerificationResponse;
import br.com.fstmkt.dto.response.TokenResponse;
import br.com.fstmkt.entity.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService,
                       UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    public TokenResponse iniciarProcessoDeAutenticacao(TokenRequest tokenRequest) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword());

        Authentication auth = realizarAutenticacao(authentication);

        Usuario usuario = extrairUsuario(auth);

        return tokenService.gerarTokenJwt(usuario);
    }

    // Para o RefreshToken
//    public TokenResponse atualizarAutenticacao() {
//
//    }

    public TokenResponse realizarAutenticacaoComToken(String token) {
        Usuario usuario = usuarioService.buscarUsuarioPorToken(token);
        return tokenService.gerarTokenJwt(usuario);
    }

    private Authentication realizarAutenticacao(UsernamePasswordAuthenticationToken authentication) {
        return authenticationManager.authenticate(authentication);
    }

    private Usuario extrairUsuario(Authentication auth) {
        return (Usuario) auth.getPrincipal();
    }

    public static boolean validarReCaptcha(String tokenCaptcha) {
        String verificationUrl = "https://www.google.com/recaptcha/api/siteverify";
        String recaptchaSecretKey = "6LdD1RkpAAAAAKNdUN1kTZzecYfmREHTYD37v4bg";
        String params = "?secret=" + recaptchaSecretKey + "&response=" + tokenCaptcha;

        RestTemplate restTemplate = new RestTemplate();
        RecaptchaVerificationResponse verificationResponse = restTemplate.postForObject(
                verificationUrl + params, null, RecaptchaVerificationResponse.class);

        return verificationResponse != null && verificationResponse.isSuccess();
    }
}
