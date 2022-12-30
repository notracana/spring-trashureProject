package com.project.trashure.detalletransaccion.infrastructure.repository.jpa;

import com.project.trashure.detalletransaccion.domain.DetalleTransaccion;
import com.project.trashure.detalletransaccion.domain.DetalleTransaccionJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleTransaccionRepositoryJpa extends JpaRepository<DetalleTransaccion, String> {
}
