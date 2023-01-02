package com.project.trashure.transaccion.infrastructure.repository;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.producto.infrastructure.repository.jpa.ProductoRepositoryJpa;
import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import com.project.trashure.transaccion.infrastructure.repository.jpa.TransaccionRepositoryJpa;
import com.project.trashure.transaccion.infrastructure.repository.port.FindTransaccionPort;
import com.project.trashure.transaccion.infrastructure.repository.port.SaveTransaccionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class SaveTransaccionRepository implements SaveTransaccionPort {
    private TransaccionRepositoryJpa transaccionRepositoryJpa;

    private FindTransaccionPort findTransaccionPort;
    @Override
    public Transaccion save(Transaccion transaccion) {
        TransaccionJpa transaccionJpa = new TransaccionJpa(transaccion);
        TransaccionJpa transaccionJpaSaved = transaccionRepositoryJpa.saveAndFlush(transaccionJpa);
        return new Transaccion(transaccionJpaSaved);
        //return transaccionRepositoryJpa.save(transaccion);
    }


    @Override
    public String generarNumeroOrden(int num){


        /* ESTO ERA LO MÍO
        * int num = 0;
        List<Transaccion> transaccionList = findTransaccionPort.findAll();
        int posicion = transaccionList.size() -1  ;
        num = Integer.parseInt(transaccionList.get(posicion).getIdTransaccion()) + 1;


        return String.valueOf(num);
        * */
        String numeroTransaccion=String.valueOf(num);
        int numDigitos=numeroTransaccion.length();

        for (int j=numDigitos; j<=9; j++){
            numeroTransaccion="0"+numeroTransaccion;}

        return numeroTransaccion;
    }
}
