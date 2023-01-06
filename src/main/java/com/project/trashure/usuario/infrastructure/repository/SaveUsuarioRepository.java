package com.project.trashure.usuario.infrastructure.repository;

import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import com.project.trashure.usuario.infrastructure.repository.jpa.UsuarioRepositoryJpa;
import com.project.trashure.usuario.infrastructure.repository.port.SaveUsuarioPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class SaveUsuarioRepository implements SaveUsuarioPort {

    UsuarioRepositoryJpa usuarioRepositoryJpa;

    @Override
    public Usuario save(Usuario usuario) {

        UsuarioJpa usuarioJpa = new UsuarioJpa(usuario);

        return new Usuario(usuarioRepositoryJpa.save(usuarioJpa));
    }
}
