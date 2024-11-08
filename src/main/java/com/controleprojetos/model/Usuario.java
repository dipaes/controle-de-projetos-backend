package com.controleprojetos.model;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario_tb")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário não pode estar vazio")
    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario;

    @NotBlank(message = "A senha não pode estar vazia")
    @Column(name = "senha", nullable = false)
    private String senha;

    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "O email deve ser válido")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "data_cadastro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    // Construtor padrão
    public Usuario() {}

    // Construtor com parâmetros
    public Usuario(Long id, String usuario, String senha, String email) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.email = email;
    }

    @PrePersist
    protected void onCreate() {
        dataCadastro = new Date();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
