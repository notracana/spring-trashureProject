package com.project.trashure.transaccion.domain;

import com.project.trashure.detalletransaccion.domain.DetalleTransaccion;
import com.project.trashure.detalletransaccion.domain.DetalleTransaccionJpa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="transacciones")
@Getter
@Setter
public class TransaccionJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_transaccion")
    private String idTransaccion;

    @Column (name = "id_vendedor")
    private String idVendedor;
    @Column (name = "id_comprador")
    private String idComprador;

    @Column (name = "estado")
    private String estado;

    @Column (name = "numero")
    private String numero;

    @Column (name = "fecha_transaccion")
    private Date fechaTransaccion;

    @Column (name = "total")
    private double total;

    @OneToOne (mappedBy = "id_transaccion")
    private DetalleTransaccion detalleTransaccion;

    public TransaccionJpa (Transaccion transaccion){
        if(transaccion == null){return;}
        this.setIdTransaccion(transaccion.getIdTransaccion());
        this.setIdVendedor(transaccion.getIdVendedor());
        this.setIdComprador(transaccion.getIdComprador());
        this.setEstado(transaccion.getEstado());
        this.setNumero(transaccion.getNumero());
        this.setFechaTransaccion(transaccion.getFechaTransaccion());
    }
}
