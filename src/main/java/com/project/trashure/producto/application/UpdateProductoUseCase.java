package com.project.trashure.producto.application;

import com.project.trashure.producto.application.port.UpdateProductoPort;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProductoUseCase implements UpdateProductoPort {

    private FindProductoPort findProductoPort;
    private SaveProductoPort saveProductoPort;
    @Override
    public Producto update(String idProducto, String nombre, String descripcion, String estado, String imagen, String precio, Integer cantidad) throws Exception {
        Producto producto = findProductoPort.findById(idProducto);
        if(nombre!=null) producto.setNombre(nombre);
        if(descripcion!=null) producto.setDescripcion(descripcion);
        if(estado!= null)producto.setEstado(estado);
        if(imagen!=null) producto.setImagen(imagen);
        if(precio!=null)producto.setPrecio(precio);
        if(cantidad!=null) producto.setCantidad(cantidad);
        return saveProductoPort.save(producto);
    }
}
