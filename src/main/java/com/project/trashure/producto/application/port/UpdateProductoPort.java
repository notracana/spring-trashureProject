package com.project.trashure.producto.application.port;

import com.project.trashure.producto.domain.Producto;

public interface UpdateProductoPort {
    Producto update(Integer idProducto, String nombre, String descripcion, String estado, String imagen) throws Exception;


}
