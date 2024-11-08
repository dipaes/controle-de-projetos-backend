package com.controleprojetos.repository;

import com.controleprojetos.model.Projeto;
import java.util.List;

public interface ProjetoRepositoryQueries {
    List<Projeto> buscarProjetos(String codigoProjeto, String nomeProj);
}
