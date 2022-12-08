package com.project.trashure.producto.infrastructure.controller.dto.output;

import com.project.trashure.producto.domain.Producto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductoOutputDTO implements Serializable {
    private String idProducto;

    private String nombre;

    private String descripcion;

    private String estado;

    private String imagen;
    private String precio;

    private Integer cantidad;

    private String idUsuario;

    public ProductoOutputDTO(Producto producto){
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.estado = producto.getEstado();
        this.imagen = producto.getImagen();
        this.precio = producto.getPrecio();
        this.cantidad = producto.getCantidad();

        //MIRAR ESTO:
        // NO SÉ SI INTERESA TENER EL ID DEL USUARIO O MÁS BIEN EL NOMNRE, DE MANERA QUE HABRÍA QUE ENCONTRAR LA FORMA
        //DE OBTENERLO
        this.idUsuario = producto.getIdUsuario();
    }

}
