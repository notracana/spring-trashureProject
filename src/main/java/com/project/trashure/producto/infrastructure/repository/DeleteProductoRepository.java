package com.project.trashure.producto.infrastructure.repository;

import com.project.trashure.producto.infrastructure.repository.jpa.ProductoRepositoryJpa;
import com.project.trashure.producto.infrastructure.repository.port.DeleteProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@AllArgsConstructor
public class DeleteProductoRepository implements DeleteProductoPort {

    private ProductoRepositoryJpa productoRepositoryJpa;
    @Override
    public void deleteById(Integer idProducto) {
        productoRepositoryJpa.deleteById(idProducto);

    }
}
