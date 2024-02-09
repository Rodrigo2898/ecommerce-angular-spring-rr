package br.com.fstmkt.dto;

import br.com.fstmkt.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class UsuarioDTO extends BaseDTO<Long> {

    private Long id;

    @CPF
    @NotBlank(message = "CPF é obrigatório.")
    private String cpf;
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;
    @Email
    @NotBlank(message = "Email é obrigatório.")
    private String email;
    @NotNull(message = "Data é obrigatório.")
    @Past(message = "Data inválida.")
    @DateTimeFormat(pattern = "ddMMyyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy")
    private LocalDate dataNascimento;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String confirmPassword;
    @NotNull(message = "Tipo de usuário é obrigatório.")
    private Set<Role> roles = new TreeSet<>();


    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String cpf, String nome, String email, LocalDate dataNascimento, String password,
                      String confirmPassword, Set<Role> roles) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
    }

    public UsuarioDTO(String cpf, String nome, String email, LocalDate dataNascimento, String password,
                      String confirmPassword, Set<Role> roles) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
