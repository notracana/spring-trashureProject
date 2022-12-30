package com.project.trashure.usuario.application;

import com.project.trashure.usuario.application.port.CreateUsuarioPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import com.project.trashure.usuario.infrastructure.repository.jpa.UsuarioRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUsuarioUseCase implements CreateUsuarioPort {

    private UsuarioRepositoryJpa usuarioRepositoryJpa;
    @Override
    public Usuario create(Usuario usuario) {
        UsuarioJpa usuarioJpa = new UsuarioJpa(usuario);
        return new Usuario(usuarioRepositoryJpa.save(usuarioJpa));
    }
}
