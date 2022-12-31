package com.project.trashure.transaccion.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Transaccion {
    private String idTransaccion;
    private String idVendedor;
    private String idComprador;

    //El estado puede ser "pendiente", "aceptada", "rechazada"
    private String estado;
    private String numero;
    private Date fechaTransaccion;
    private Date fechaRecepcion;

    private double total;

    public Transaccion (TransaccionJpa transaccionJpa){
        if(transaccionJpa == null){return;}
        this.setIdTransaccion(transaccionJpa.getIdTransaccion());
        this.setIdVendedor(transaccionJpa.getIdVendedor());
        this.setIdComprador(transaccionJpa.getIdComprador());
        this.setEstado(transaccionJpa.getEstado());
        this.setNumero(transaccionJpa.getNumero());
        this.setFechaTransaccion(transaccionJpa.getFechaTransaccion());
    }


}
