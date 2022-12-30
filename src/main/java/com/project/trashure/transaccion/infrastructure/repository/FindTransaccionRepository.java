package com.project.trashure.transaccion.infrastructure.repository;

import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.infrastructure.repository.jpa.TransaccionRepositoryJpa;
import com.project.trashure.transaccion.infrastructure.repository.port.FindTransaccionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FindTransaccionRepository implements FindTransaccionPort {

    private TransaccionRepositoryJpa transaccionRepositoryJpa;
    @Override
    public List<Transaccion> findAll() {
        return transaccionRepositoryJpa.findAll();
    }
}
