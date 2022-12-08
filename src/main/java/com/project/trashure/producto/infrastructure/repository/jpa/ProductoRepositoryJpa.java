package com.project.trashure.producto.infrastructure.repository.jpa;

import com.project.trashure.producto.domain.ProductoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositoryJpa extends JpaRepository<ProductoJpa, String> {
}
