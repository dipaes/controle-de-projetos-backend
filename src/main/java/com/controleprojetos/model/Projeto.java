package com.controleprojetos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@Data
@Entity
@Table(name = "projeto_tb")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_projeto")
    private String codigoProjeto;

    @Column(name = "nome_proj")
    private String nomeProj;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "dt_ini_proj")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dtIniProj;

    @Column(name = "dt_fim_proj")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dtFimProj;

    @Column(name = "status_proj")
    private String statusProj;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "data_cadastro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCadastro;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<Tarefa> tarefas;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<Custo> custos;

    @ManyToMany
    @JoinTable(
        name = "projeto_fornecedor_tb",
        joinColumns = @JoinColumn(name = "projeto_id"),
        inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
    )
    @JsonIgnore
    private List<Fornecedor> fornecedores;

    @PrePersist
    protected void onCreate() {
        dataCadastro = new Date();
    }
}
