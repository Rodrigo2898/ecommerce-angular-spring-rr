package br.com.fstmkt.dto.response;

public class TokenResponse {
    private String token;
    private Integer expiresIn;
    private String tokenType = "Bearer";

    public TokenResponse() {
    }

    public TokenResponse(String token, Integer expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
