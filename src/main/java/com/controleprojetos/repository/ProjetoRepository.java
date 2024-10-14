package com.controleprojetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.controleprojetos.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
