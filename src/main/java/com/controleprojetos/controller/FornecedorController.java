package com.controleprojetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.controleprojetos.model.Fornecedor;
import com.controleprojetos.service.FornecedorService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/fornecedor")
public class FornecedorController {
    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAllFornecedores() {
        try {
            List<Fornecedor> fornecedores = fornecedorService.getAllFornecedores();
            return ResponseEntity.ok(fornecedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Long id) {
        try {
            Optional<Fornecedor> fornecedor = fornecedorService.getFornecedorById(id);
            if (fornecedor.isPresent()) {
                return ResponseEntity.ok(fornecedor.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Fornecedor> createFornecedor(@RequestBody Fornecedor fornecedor) {
        try {
            Fornecedor novoFornecedor = fornecedorService.createFornecedor(fornecedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoFornecedor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedorDetails) {
        try {
            Optional<Fornecedor> fornecedor = fornecedorService.getFornecedorById(id);
            if (fornecedor.isPresent()) {
                Fornecedor fornecedorAtualizado = fornecedor.get();
                if (fornecedorDetails.getNomeFornecedor() != null) {
                    fornecedorAtualizado.setNomeFornecedor(fornecedorDetails.getNomeFornecedor());
                }
                if (fornecedorDetails.getNomeEmpresa() != null) {
                    fornecedorAtualizado.setNomeEmpresa(fornecedorDetails.getNomeEmpresa());
                }
                if (fornecedorDetails.getEmail() != null) {
                    fornecedorAtualizado.setEmail(fornecedorDetails.getEmail());
                }
                if (fornecedorDetails.getCargo() != null) {
                    fornecedorAtualizado.setCargo(fornecedorDetails.getCargo());
                }
                fornecedorService.updateFornecedor(fornecedorAtualizado);
                return ResponseEntity.ok(fornecedorAtualizado);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        try {
            Optional<Fornecedor> fornecedor = fornecedorService.getFornecedorById(id);
            if (fornecedor.isPresent()) {
                fornecedorService.deleteFornecedor(id);
                return ResponseEntity.noContent().build();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
