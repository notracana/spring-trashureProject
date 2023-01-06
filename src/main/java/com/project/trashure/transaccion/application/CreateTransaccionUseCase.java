package com.project.trashure.transaccion.application;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import com.project.trashure.transaccion.application.port.CreateTransaccionPort;
import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.infrastructure.repository.port.SaveTransaccionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateTransaccionUseCase implements CreateTransaccionPort {

    private SaveTransaccionPort saveTransaccionPort;

    @Override
    public Transaccion create(Transaccion transaccion) throws Exception {
        Transaccion transaccionCreated = saveTransaccionPort.save(transaccion);
        return transaccionCreated;
    }
}
