package com.controleprojetos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "fornecedor_tb")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_fornecedor")
    private String nomeFornecedor;

    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Column(name = "email")
    private String email;

    @Column(name = "cargo")
    private String cargo;

    @ManyToMany(mappedBy = "fornecedores")
    @JsonIgnore
    private List<Projeto> projetos;
}
