package com.project.trashure.transaccion.infrastructure.repository.port;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.transaccion.domain.Transaccion;

import java.util.List;

public interface FindTransaccionPort {

    List<Transaccion> findAll();

    List<Transaccion> findAllByIdComprador(Integer idComprador);

    List<Transaccion> findAllByIdVendedor(Integer idVendedor);

    Transaccion findById(Integer idTransaccion) throws Exception;


}
