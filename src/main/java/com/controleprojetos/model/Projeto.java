package com.controleprojetos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter

@Data
@Entity
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Date dataInicio;
    private Date dataTermino;
    private String status;

    @OneToMany(mappedBy = "projeto")
    private List<Tarefa> tarefas;

    @OneToMany(mappedBy = "projeto")
    private List<Custo> custos;
}

