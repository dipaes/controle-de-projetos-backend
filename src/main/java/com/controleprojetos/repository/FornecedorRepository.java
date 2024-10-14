package com.controleprojetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.controleprojetos.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
