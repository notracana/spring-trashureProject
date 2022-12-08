package com.project.trashure.producto.infrastructure.repository;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.producto.infrastructure.repository.jpa.ProductoRepositoryJpa;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class SaveProductoRepository implements SaveProductoPort {

    private ProductoRepositoryJpa productoRepositoryJpa;
    @Override
    public Producto save(Producto producto) {
        ProductoJpa productoJpa = new ProductoJpa(producto);
        ProductoJpa productoJpaSaved = productoRepositoryJpa.saveAndFlush(productoJpa);
        return new Producto(productoJpaSaved);
    }
}
