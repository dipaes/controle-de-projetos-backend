package com.controleprojetos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controleprojetos.model.Fornecedor;
import com.controleprojetos.repository.FornecedorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> getFornecedorById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public Fornecedor createFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor updateFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public void deleteFornecedor(Long id) {
        fornecedorRepository.deleteById(id);
    }
}
