package com.project.trashure.transaccion.domain;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.usuario.domain.UsuarioJpa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name="transacciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
/*
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACCION_SEQ")
    @GenericGenerator(
            name = "TRANSACCION_SEQ",
            strategy = "com.package com.project.trashure.sequences.SequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.INCREMENT_PARAM,
                            value = "1"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER,
                            value="TRA"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER,
                            value = "%08d"
                    )
            }
    )*/
    @Column (name = "id_transaccion")
    private Integer idTransaccion;

    @Column (name = "id_vendedor")
    private Integer idVendedor;
    @Column (name = "id_comprador")
    private Integer idComprador;

    @Column (name = "estado")
    private String estado;

    /*
    @Column (name = "id_producto")
    private String idProducto;*/

    @Column (name = "fecha_transaccion")
    private Date fechaTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", updatable = false, insertable = false)
    private UsuarioJpa usuarioJpa;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="id_producto")
    private ProductoJpa productoJpa;

    //@OneToOne (mappedBy = "id_transaccion")
    //private DetalleTransaccion detalleTransaccion;

    public TransaccionJpa (Transaccion transaccion){
        if(transaccion == null){return;}
        this.setIdTransaccion(transaccion.getIdTransaccion());
        this.setIdVendedor(transaccion.getIdVendedor());
        this.setIdComprador(transaccion.getIdComprador());
        this.setEstado(transaccion.getEstado());
        //this.setIdProducto(transaccion.getIdProducto());
        this.setFechaTransaccion(transaccion.getFechaTransaccion());

        Producto producto = transaccion.getProducto();
        updateProducto(producto);

    }
    public void updateProducto(Producto producto){
        if(producto == null){
            this.setProductoJpa(null);
            return;
        }
        ProductoJpa productoJpa1 = new ProductoJpa(producto);
        this.setProductoJpa(productoJpa1);
    }
}
