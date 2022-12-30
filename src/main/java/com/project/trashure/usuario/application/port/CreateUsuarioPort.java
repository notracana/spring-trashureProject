package com.project.trashure.usuario.application.port;

import com.project.trashure.usuario.domain.Usuario;

public interface CreateUsuarioPort {

    Usuario create(Usuario usuario);
}
