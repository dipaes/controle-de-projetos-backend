package com.controleprojetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.controleprojetos.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
