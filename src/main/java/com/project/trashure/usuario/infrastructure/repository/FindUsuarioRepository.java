package com.project.trashure.usuario.infrastructure.repository;

import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import com.project.trashure.usuario.infrastructure.repository.jpa.UsuarioRepositoryJpa;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class FindUsuarioRepository implements FindUsuarioPort {
    private UsuarioRepositoryJpa usuarioRepositoryJpa;

    @Override
    public Usuario findById(String idUsuario) throws Exception {
        UsuarioJpa usuarioJpa = usuarioRepositoryJpa.findById(idUsuario)
                .orElseThrow(()-> new Exception ("No se ha encontrado ningún usuario con el id "
                        + idUsuario));
        return new Usuario(usuarioJpa);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepositoryJpa.findByUsername(username);

    }


}
