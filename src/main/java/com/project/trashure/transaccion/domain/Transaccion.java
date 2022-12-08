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
    private String numero;
    private Date fechaTransaccion;
    private Date fechaRecepcion;

    private double total;


}
