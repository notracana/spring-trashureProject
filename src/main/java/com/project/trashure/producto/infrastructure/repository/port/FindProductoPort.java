package com.project.trashure.producto.infrastructure.repository.port;

import com.project.trashure.producto.domain.Producto;

import java.util.List;

public interface FindProductoPort {

    Producto findById(Integer idProducto) throws Exception;

    List<Producto> findAll ();
}
