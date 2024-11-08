package com.controleprojetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.controleprojetos.exception.ProjetoNotFoundException;
import com.controleprojetos.model.Projeto;
import com.controleprojetos.model.Usuario;
import com.controleprojetos.model.Fornecedor;
import com.controleprojetos.service.ProjetoService;
import com.controleprojetos.repository.UsuarioRepository;
import com.controleprojetos.repository.FornecedorRepository;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.logging.Logger;
@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private static final Logger logger = Logger.getLogger(ProjetoController.class.getName());

    @GetMapping
    public ResponseEntity<List<Projeto>> getAllProjetos() {
        List<Projeto> projetos = projetoService.getAllProjetos();
        return ResponseEntity.ok(projetos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjetoById(@PathVariable Long id) {
        try {
            Projeto projeto = projetoService.getProjetoById(id)
                .orElseThrow(() -> new ProjetoNotFoundException("Projeto não encontrado com id: " + id));
            return ResponseEntity.ok(projeto);
        } catch (ProjetoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createProjeto(@RequestBody Projeto projeto, @RequestParam List<Long> fornecedorIds) {
        try {
            // Obter o usuário autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String usuarioLogado = authentication.getName();

            // Buscar o usuário no banco de dados
            Usuario usuario = usuarioRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Associar o usuário ao projeto
            projeto.setUsuario(usuario.getUsuario());

            // Associar fornecedores ao projeto
            List<Fornecedor> fornecedores = fornecedorRepository.findAllById(fornecedorIds);
            projeto.setFornecedores(fornecedores);

            // Salvar o projeto no banco de dados
            Projeto novoProjeto = projetoService.createProjeto(projeto);
            return new ResponseEntity<>(novoProjeto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar projeto: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateProjeto(@PathVariable Long id, @RequestBody Projeto projetoDetails) {
        try {
            Projeto projeto = projetoService.getProjetoById(id)
                .orElseThrow(() -> new ProjetoNotFoundException("Projeto não encontrado com id: " + id));

            if (projetoDetails.getNomeProj() != null) {
                projeto.setNomeProj(projetoDetails.getNomeProj());
            }
            if (projetoDetails.getDescricao() != null) {
                projeto.setDescricao(projetoDetails.getDescricao());
            }
            if (projetoDetails.getStatusProj() != null) {
                projeto.setStatusProj(projetoDetails.getStatusProj());
            }

            Projeto projetoAtualizado = projetoService.updateProjeto(id, projeto);
            return ResponseEntity.ok(projetoAtualizado);
        } catch (ProjetoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar projeto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProjeto(@PathVariable Long id) {
        try {
            projetoService.deleteProjeto(id);
            return ResponseEntity.noContent().build();
        } catch (ProjetoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao deletar projeto: " + e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarProjetos(@RequestParam(required = false) String codigoProjeto) {
        try {
            if (codigoProjeto == null || codigoProjeto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisição invalida.");
            }
            logger.info("Recebendo requisição de busca com código: " + codigoProjeto);
            List<Object[]> projetos = projetoService.buscarProjetos(codigoProjeto);
            return ResponseEntity.ok(projetos);
        } catch (ProjetoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao buscar projetos: " + e.getMessage());
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        logger.warning(name + " parâmetro está faltando");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parâmetro de requisição inválido: " + name);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        logger.warning(name + " parâmetro tem um tipo incorreto");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parâmetro de requisição inválido: " + name);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFound(NoHandlerFoundException ex) {
        logger.warning("URL inválida: " + ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL inválida: " + ex.getRequestURL());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        logger.severe("Erro inesperado: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + ex.getMessage());
    }
}
