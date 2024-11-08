package com.controleprojetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.controleprojetos.model.Tarefa;
import com.controleprojetos.repository.TarefaRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {
    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping
    public ResponseEntity<List<Tarefa>> getAllTarefas() {
        try {
            List<Tarefa> tarefas = tarefaRepository.findAll();
            return ResponseEntity.ok(tarefas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> getTarefaById(@PathVariable Long id) {
        try {
            Optional<Tarefa> tarefa = tarefaRepository.findById(id);
            if (tarefa.isPresent()) {
                return ResponseEntity.ok(tarefa.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Tarefa> createTarefa(@RequestBody Tarefa tarefa) {
        try {
            Tarefa novaTarefa = tarefaRepository.save(tarefa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> updateTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetails) {
        try {
            Optional<Tarefa> tarefa = tarefaRepository.findById(id);
            if (tarefa.isPresent()) {
                Tarefa tarefaAtualizada = tarefa.get();
                if (tarefaDetails.getNomeTarefa() != null) {
                    tarefaAtualizada.setNomeTarefa(tarefaDetails.getNomeTarefa());
                }
                if (tarefaDetails.getDescricao() != null) {
                    tarefaAtualizada.setDescricao(tarefaDetails.getDescricao());
                }
                if (tarefaDetails.getDtIniTarefa() != null) {
                    tarefaAtualizada.setDtIniTarefa(tarefaDetails.getDtIniTarefa());
                }
                if (tarefaDetails.getDtFimTarefa() != null) {
                    tarefaAtualizada.setDtFimTarefa(tarefaDetails.getDtFimTarefa());
                }
                if (tarefaDetails.getStatusTarefa() != null) {
                    tarefaAtualizada.setStatusTarefa(tarefaDetails.getStatusTarefa());
                }
                tarefaRepository.save(tarefaAtualizada);
                return ResponseEntity.ok(tarefaAtualizada);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id) {
        try {
            Optional<Tarefa> tarefa = tarefaRepository.findById(id);
            if (tarefa.isPresent()) {
                tarefaRepository.delete(tarefa.get());
                return ResponseEntity.noContent().build();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
