package com.project.trashure.detalletransaccion.infrastructure.repository;

import com.project.trashure.detalletransaccion.domain.DetalleTransaccion;
import com.project.trashure.detalletransaccion.infrastructure.repository.jpa.DetalleTransaccionRepositoryJpa;
import com.project.trashure.detalletransaccion.infrastructure.repository.port.SaveDetalleTransaccionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class SaveDetalleTransaccionRepository implements SaveDetalleTransaccionPort {


    private DetalleTransaccionRepositoryJpa detalleTransaccionRepositoryJpa;
    @Override
    public DetalleTransaccion save(DetalleTransaccion detalleTransaccion) {
        return detalleTransaccionRepositoryJpa.save(detalleTransaccion);
    }
}
