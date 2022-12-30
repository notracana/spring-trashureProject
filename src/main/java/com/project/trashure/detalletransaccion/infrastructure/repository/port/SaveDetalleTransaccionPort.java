package com.project.trashure.detalletransaccion.infrastructure.repository.port;

import com.project.trashure.detalletransaccion.domain.DetalleTransaccion;

public interface SaveDetalleTransaccionPort {

    DetalleTransaccion save(DetalleTransaccion detalleTransaccion);
}
