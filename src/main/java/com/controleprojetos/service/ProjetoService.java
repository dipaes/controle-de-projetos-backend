package com.controleprojetos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controleprojetos.model.Projeto;
import com.controleprojetos.repository.ProjetoRepository;
import java.util.List;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto createProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }
}
