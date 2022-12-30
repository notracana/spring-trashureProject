package com.project.trashure.transaccion.infrastructure.repository.jpa;

import com.project.trashure.transaccion.domain.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepositoryJpa extends JpaRepository<Transaccion, String> {
}
