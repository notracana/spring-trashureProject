package com.project.trashure.producto.infrastructure.repository.jpa;

import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import com.project.trashure.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepositoryJpa extends JpaRepository<ProductoJpa, Integer> {

    List<ProductoJpa> findAllByPropietario(Usuario propietario);
}
