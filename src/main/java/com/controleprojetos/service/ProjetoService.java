package com.controleprojetos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controleprojetos.model.Projeto;
import com.controleprojetos.repository.ProjetoRepository;
import com.controleprojetos.exception.ProjetoNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(ProjetoService.class.getName());

    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> getProjetoById(Long id) {
        return projetoRepository.findById(id);
    }

    public Projeto createProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Projeto updateProjeto(Long id, Projeto projeto) {
        Projeto projetoExistente = projetoRepository.findById(id)
            .orElseThrow(() -> new ProjetoNotFoundException("Projeto não encontrado com id: " + id));
        projetoExistente.setNomeProj(projeto.getNomeProj());
        projetoExistente.setDescricao(projeto.getDescricao());
        // Atualize outros campos conforme necessário
        return projetoRepository.save(projetoExistente);
    }

    public void deleteProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id)
            .orElseThrow(() -> new ProjetoNotFoundException("Projeto não encontrado com id: " + id));
        projetoRepository.delete(projeto);
    }

    // Novo método para buscar projetos usando CriteriaQuery e junções
    public List<Object[]> buscarProjetos(String codigoProjeto) {
        logger.info("Iniciando busca de projetos com código: " + codigoProjeto);
        if (codigoProjeto == null || codigoProjeto.isEmpty()) {
            throw new IllegalArgumentException("O código do projeto não pode estar vazio.");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Projeto> root = criteriaQuery.from(Projeto.class);

        // Junções com tabelas relacionadas
        Join<Object, Object> tarefaJoin = root.join("tarefas", JoinType.LEFT);
        Join<Object, Object> custoJoin = root.join("custos", JoinType.LEFT);
        Join<Object, Object> fornecedorJoin = custoJoin.join("fornecedor", JoinType.LEFT);

        // Seleção dos campos desejados
        criteriaQuery.multiselect(
            root.get("codigoProjeto"),
            root.get("nomeProj"),
            root.get("dtIniProj"),
            root.get("dtFimProj"),
            tarefaJoin.get("nomeTarefa"),
            tarefaJoin.get("statusTarefa"),
            custoJoin.get("valor"),
            custoJoin.get("centroCusto"),
            custoJoin.get("horasTrabalhadas"),
            fornecedorJoin.get("nomeFornecedor"),
            fornecedorJoin.get("nomeEmpresa")
        );

        // Condição de filtro
        Predicate predicate = criteriaBuilder.equal(root.get("codigoProjeto"), codigoProjeto);
        criteriaQuery.where(predicate);

        List<Object[]> resultados = entityManager.createQuery(criteriaQuery).getResultList();
        if (resultados.isEmpty()) {
            throw new ProjetoNotFoundException("Projeto não encontrado com o código: " + codigoProjeto);
        }

        logger.info("Busca concluída. Número de projetos encontrados: " + resultados.size());
        return resultados;
    }
}
