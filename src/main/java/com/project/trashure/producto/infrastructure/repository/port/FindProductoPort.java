package com.project.trashure.producto.infrastructure.repository.port;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.usuario.domain.Usuario;

import java.util.List;

public interface FindProductoPort {

    Producto findById(Integer idProducto) throws Exception;

    List<Producto> findAll ();

    List<Producto> findAllByPropietario (Usuario propietario);
}
