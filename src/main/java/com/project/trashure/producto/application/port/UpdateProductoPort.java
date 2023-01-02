package com.project.trashure.producto.application.port;

import com.project.trashure.producto.domain.Producto;

public interface UpdateProductoPort {
    Producto update(String idProducto, String nombre, String descripcion, String estado, String imagen) throws Exception;


}
