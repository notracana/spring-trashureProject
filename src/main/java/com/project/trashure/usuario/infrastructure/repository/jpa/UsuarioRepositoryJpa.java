package com.project.trashure.usuario.infrastructure.repository.jpa;

import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositoryJpa extends JpaRepository<UsuarioJpa, Integer> {
    UsuarioJpa findByUsername(String username);

    UsuarioJpa findByEmail(String email);

    List<UsuarioJpa> findAll();
}
