package com.project.trashure.usuario.infrastructure.repository.port;


import com.project.trashure.usuario.domain.Usuario;

import java.util.List;

public interface FindUsuarioPort {
    Usuario findById(String idUsuario) throws Exception;

    Usuario findByUsername(String username);

    List<Usuario> findAll();


}
