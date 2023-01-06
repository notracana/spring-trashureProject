package com.project.trashure.transaccion.application.port;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.transaccion.domain.Transaccion;

public interface CreateTransaccionPort {

    Transaccion create(Transaccion transaccion) throws Exception;

}
