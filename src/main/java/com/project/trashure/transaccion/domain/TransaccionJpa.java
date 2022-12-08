package com.project.trashure.transaccion.domain;

import com.project.trashure.detalletransaccion.domain.DetalleTransaccion;
import com.project.trashure.detalletransaccion.domain.DetalleTransaccionJpa;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="transacciones")
public class TransaccionJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_transaccion")
    private String idTransaccion;

    @Column (name = "id_vendedor")
    private String idVendedor;
    @Column (name = "id_comprador")
    private String idComprador;

    @Column (name = "numero")
    private String numero;

    @Column (name = "fecha_transaccion")
    private Date fechaTransaccion;

    @Column (name = "fecha_recepcion")
    private Date fechaRecepcion;

    @Column (name = "total")
    private double total;

    @OneToOne (mappedBy = "id_transaccion")
    private DetalleTransaccion detalleTransaccion;
}
