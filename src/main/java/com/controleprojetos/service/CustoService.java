package com.controleprojetos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controleprojetos.model.Custo;
import com.controleprojetos.repository.CustoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustoService {
    @Autowired
    private CustoRepository custoRepository;

    public List<Custo> getAllCustos() {
        return custoRepository.findAll();
    }

    public Optional<Custo> getCustoById(Long id) {
        return custoRepository.findById(id);
    }

    public Custo createCusto(Custo custo) {
        return custoRepository.save(custo);
    }

    public Custo updateCusto(Custo custo) {
        return custoRepository.save(custo);
    }

    public void deleteCusto(Long id) {
        custoRepository.deleteById(id);
    }
}
