package com.project.trashure.producto.infrastructure.repository;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.producto.infrastructure.repository.jpa.ProductoRepositoryJpa;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindProductoRepository implements FindProductoPort {
    private ProductoRepositoryJpa productoRepositoryJpa;
    @Override
    public Producto findById(Integer idProducto) throws Exception {
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

    @Override
    public List<Producto> findAllByPropietario(Usuario propietario) {
        UsuarioJpa propietarioJpa = new UsuarioJpa(propietario);
        List<ProductoJpa> productoJpaList = productoRepositoryJpa.findAllByPropietarioJpa(propietarioJpa);
        return productoJpaList.stream().map(Producto::new).collect(Collectors.toList());
    }

    @Override
    public List<Producto> findAllByIdUsuario(Integer idUsuario) {
        List<ProductoJpa> productoJpaList = productoRepositoryJpa.findAllByIdUsuario(idUsuario);
        return productoJpaList.stream().map(Producto::new).collect(Collectors.toList());
    }


}
