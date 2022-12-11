package com.project.trashure.producto.infrastructure.repository;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.producto.infrastructure.repository.jpa.ProductoRepositoryJpa;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindProductoRepository implements FindProductoPort {
    private ProductoRepositoryJpa productoRepositoryJpa;
    @Override
    public Producto findById(String idProducto) throws Exception {
        ProductoJpa productoJpa = productoRepositoryJpa.findById(idProducto)
                .orElseThrow(()-> new Exception ("No se ha encontrado ningún producto con el id " + idProducto));
        return new Producto(productoJpa);
    }

    @Override
    public List<Producto> findAll() {
        List<ProductoJpa> productoJpaList= productoRepositoryJpa.findAll();
        List<Producto> productoList = productoJpaList.stream().map(Producto::new).collect(Collectors.toList());
        return productoList;
    }
}
