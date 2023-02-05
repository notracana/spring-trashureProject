package com.project.trashure.usuario.application.port;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.usuario.domain.Usuario;

public interface UpdateUsuarioPort {
    Usuario update(Integer idUsuario, String nombre, String apellidos, String email, String telefono, String direccion, String localidad) throws Exception;

}
