package com.project.trashure.producto.infrastructure.repository.port;

import com.project.trashure.producto.domain.Producto;

public interface FindProductoPort {

    Producto findById(String idProducto) throws Exception;
}
