package com.controleprojetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.controleprojetos.model.Custo;
import com.controleprojetos.service.CustoService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/custos")
public class CustoController {
    @Autowired
    private CustoService custoService;

    @GetMapping
    public ResponseEntity<List<Custo>> getAllCustos() {
        try {
            List<Custo> custos = custoService.getAllCustos();
            return ResponseEntity.ok(custos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Custo> getCustoById(@PathVariable Long id) {
        try {
            Optional<Custo> custo = custoService.getCustoById(id);
            if (custo.isPresent()) {
                return ResponseEntity.ok(custo.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Custo não encontrado");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Custo> createCusto(@RequestBody Custo custo) {
        try {
            Custo novoCusto = custoService.createCusto(custo);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCusto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Custo> updateCusto(@PathVariable Long id, @RequestBody Custo custoDetails) {
        try {
            Optional<Custo> custo = custoService.getCustoById(id);
            if (custo.isPresent()) {
                Custo custoAtualizado = custo.get();
                if (custoDetails.getData() != null) {
                    custoAtualizado.setData(custoDetails.getData());
                }
                if (custoDetails.getValor() != null) {
                    custoAtualizado.setValor(custoDetails.getValor());
                }
                if (custoDetails.getHorasTrabalhadas() != null) {
                    custoAtualizado.setHorasTrabalhadas(custoDetails.getHorasTrabalhadas());
                }
                custoService.updateCusto(custoAtualizado);
                return ResponseEntity.ok(custoAtualizado);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Custo não encontrado");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCusto(@PathVariable Long id) {
        try {
            Optional<Custo> custo = custoService.getCustoById(id);
            if (custo.isPresent()) {
                custoService.deleteCusto(id);
                return ResponseEntity.noContent().build();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Custo não encontrado");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
