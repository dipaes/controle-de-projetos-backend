package com.controleprojetos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "custo_tb")
public class Custo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date data;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "horas_trabalhadas")
    private Integer horasTrabalhadas;

    @Column(name = "centro_custo")
    private String centroCusto;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    @JsonBackReference("projeto-custo")
    private Projeto projeto;
}
