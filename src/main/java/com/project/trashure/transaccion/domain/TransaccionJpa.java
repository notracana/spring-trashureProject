package com.project.trashure.transaccion.domain;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.usuario.domain.UsuarioJpa;
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

    /*
    @Column (name = "id_producto")
    private String idProducto;*/

    @Column (name = "fecha_transaccion")
    private Date fechaTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", updatable = false, insertable = false)
    private UsuarioJpa usuarioJpa;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
