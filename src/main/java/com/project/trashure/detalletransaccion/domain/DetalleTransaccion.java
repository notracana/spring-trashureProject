package com.project.trashure.detalletransaccion.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleTransaccion {
    private String idDetalleTransaccion;
    private String idTransaccion;
    private int cantidad;
    private double precio;
    private double total;
    private String idProducto;

}
