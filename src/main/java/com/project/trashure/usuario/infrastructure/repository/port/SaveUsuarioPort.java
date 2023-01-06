package com.project.trashure.usuario.infrastructure.repository.port;

import com.project.trashure.usuario.domain.Usuario;

public interface SaveUsuarioPort {

    Usuario save (Usuario usuario);
}
