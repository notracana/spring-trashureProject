package com.project.trashure.usuario.infrastructure.repository;

import com.project.trashure.producto.infrastructure.repository.jpa.ProductoRepositoryJpa;
import com.project.trashure.usuario.infrastructure.repository.jpa.UsuarioRepositoryJpa;
import com.project.trashure.usuario.infrastructure.repository.port.DeleteUsuarioPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DeleteUsuarioRepository implements DeleteUsuarioPort {

    private UsuarioRepositoryJpa usuarioRepositoryJpa;
    @Override
    public void deleteById(Integer idUsuario) {
        usuarioRepositoryJpa.deleteById(idUsuario);

    }
}
