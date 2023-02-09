package com.project.trashure.producto.application;

import com.project.trashure.producto.application.port.CreateProductoPort;
import com.project.trashure.producto.domain.Producto;

import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProductoUseCase implements CreateProductoPort {

    //MIRAR ESTO:
    //REVISAR ESTA CLASE EN GENERAL

    private FindProductoPort findProductoPort;
    private SaveProductoPort saveProductoPort;

    @Override
    public Producto create(Producto producto) throws Exception {
        System.out.println("producto a crear: ");
        System.out.println("nombre " + producto.getNombre());
        System.out.println("id usuario " + producto.getIdUsuario());
        Producto productoCreated = saveProductoPort.save(producto);

        System.out.println("producto una veza creado: ");
        System.out.println("id : " + productoCreated.getIdProducto());
        System.out.println("idusuario " + productoCreated.getIdUsuario());
        return productoCreated;
    }
}
