package br.com.fstmkt.controller;

import br.com.fstmkt.dto.request.TokenRequest;
import br.com.fstmkt.dto.response.TokenResponse;
import br.com.fstmkt.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<TokenResponse> gerarToken(@Valid TokenRequest tokenRequest) {
        if (!authService.validarReCaptcha(tokenRequest.getTokenCaptcha())) {
            return ResponseEntity.badRequest().body(null);
        }
        var token = authService.iniciarProcessoDeAutenticacao(tokenRequest);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/{token}")
    public ResponseEntity<TokenResponse> entrarComTokenUsuario(@PathVariable String token) {
        return ResponseEntity.ok().body(authService.realizarAutenticacaoComToken(token));
    }
}
