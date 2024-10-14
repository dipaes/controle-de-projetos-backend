package com.controleprojetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.controleprojetos.model.Custo;
import com.controleprojetos.service.CustoService;
import java.util.List;

@RestController
@RequestMapping("/api/custos")
public class CustoController {
    @Autowired
    private CustoService custoService;

    @GetMapping
    public List<Custo> getAllCustos() {
        return custoService.getAllCustos();
    }

    @PostMapping
    public Custo createCusto(@RequestBody Custo custo) {
        return custoService.createCusto(custo);
    }
}
