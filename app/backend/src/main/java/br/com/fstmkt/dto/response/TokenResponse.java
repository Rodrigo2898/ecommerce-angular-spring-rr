package br.com.fstmkt.dto.response;

import java.util.List;

public class TokenResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long id;
    private String username;
    private String cpf;
    private String email;
    private List<String> roles;

    public TokenResponse() {
    }

    public TokenResponse(String token, Long id, String username, String cpf, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.cpf = cpf;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
