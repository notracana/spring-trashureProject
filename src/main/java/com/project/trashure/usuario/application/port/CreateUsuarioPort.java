package com.project.trashure.usuario.application.port;

import com.project.trashure.usuario.domain.Usuario;

import java.security.NoSuchAlgorithmException;

public interface CreateUsuarioPort {

    Usuario create(Usuario usuario) throws Exception;
}
