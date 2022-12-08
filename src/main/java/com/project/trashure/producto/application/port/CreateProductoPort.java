package com.project.trashure.producto.application.port;

import com.project.trashure.producto.domain.Producto;

public interface CreateProductoPort {

    //MIRAR ESTO:
    //AQUÍ USAR SAVEPRODUCTO
    Producto create(Producto producto) throws Exception;
}
