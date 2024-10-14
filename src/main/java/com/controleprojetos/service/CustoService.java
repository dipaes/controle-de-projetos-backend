package com.controleprojetos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controleprojetos.model.Custo;
import com.controleprojetos.repository.CustoRepository;
import java.util.List;

@Service
public class CustoService {
    @Autowired
    private CustoRepository custoRepository;

    public List<Custo> getAllCustos() {
        return custoRepository.findAll();
    }

    public Custo createCusto(Custo custo) {
        return custoRepository.save(custo);
    }
}
