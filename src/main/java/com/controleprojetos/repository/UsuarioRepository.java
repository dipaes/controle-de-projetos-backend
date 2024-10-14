package com.controleprojetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.controleprojetos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsuario(String usuario);
}
