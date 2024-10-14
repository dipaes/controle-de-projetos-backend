package com.controleprojetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.controleprojetos.model.Custo;

public interface CustoRepository extends JpaRepository<Custo, Long> {
}
