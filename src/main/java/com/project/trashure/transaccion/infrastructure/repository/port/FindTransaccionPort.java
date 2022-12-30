package com.project.trashure.transaccion.infrastructure.repository.port;

import com.project.trashure.transaccion.domain.Transaccion;

import java.util.List;

public interface FindTransaccionPort {

    List<Transaccion> findAll();
}
