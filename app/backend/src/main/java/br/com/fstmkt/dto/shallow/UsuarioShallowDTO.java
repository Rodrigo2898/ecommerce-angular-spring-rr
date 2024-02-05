package br.com.fstmkt.dto.shallow;

import br.com.fstmkt.dto.BaseDTO;
import br.com.fstmkt.entity.enums.Role;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class UsuarioShallowDTO extends BaseDTO<Long> {
    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private LocalDate dataNascimento;

    private Set<Role> roles = new TreeSet<>();

    public UsuarioShallowDTO() {
    }

//    public UsuarioShallowDTO(Long id, String cpf, String nome, String email, Set<Role> roles) {
//        this.id = id;
//        this.cpf = cpf;
//        this.nome = nome;
//        this.email = email;
//        this.roles = roles;
//    }


    public UsuarioShallowDTO(Long id, String cpf, String nome, String email, LocalDate dataNascimento, Set<Role> roles) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.roles = roles;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
