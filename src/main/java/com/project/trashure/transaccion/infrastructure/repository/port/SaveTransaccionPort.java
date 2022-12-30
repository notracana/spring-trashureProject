package com.project.trashure.transaccion.infrastructure.repository.port;

import com.project.trashure.transaccion.domain.Transaccion;

public interface SaveTransaccionPort {
    Transaccion save(Transaccion transaccion);
    String generarNumeroOrden(int numero);
}
