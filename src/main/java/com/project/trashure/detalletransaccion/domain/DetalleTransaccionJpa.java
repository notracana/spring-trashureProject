package com.project.trashure.detalletransaccion.domain;

import jakarta.persistence.*;

@Entity
@Table(name="detalles_transacciones")
public class DetalleTransaccionJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_transaccion")
    private String idDetalleTransaccion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio")
    private double precio;

    @Column(name = "total")
    private double total;

    @Column(name = "id_transaccion")
    private String idTransaccion;

    @OneToMany
    private String idProducto;
}
