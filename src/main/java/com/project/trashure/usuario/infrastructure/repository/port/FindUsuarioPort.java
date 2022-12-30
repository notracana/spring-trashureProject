package com.project.trashure.usuario.infrastructure.repository.port;


import com.project.trashure.usuario.domain.Usuario;

public interface FindUsuarioPort {
    Usuario findById(String idUsuario) throws Exception;

    Usuario findByUsername(String username);


}
