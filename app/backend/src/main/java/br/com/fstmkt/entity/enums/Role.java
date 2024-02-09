package br.com.fstmkt.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role implements GrantedAuthority {
    ADMIN("Administrador"),
    VENDEDOR("Vendedor"),
    CLIENTE("Cliente"),
    USUARIO("Usuário");

    private final String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}
