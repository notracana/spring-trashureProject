package com.project.trashure.producto.domain;

import com.project.trashure.producto.infrastructure.controller.dto.input.ProductoInputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private String idProducto;

    private String nombre;

    private String descripcion;

    private String estado;

    private String imagen;

    //El campo price realmente hace referencia al tipo de venta al que está sujeto
    //porque el usuario puede elegir la modalidad de intercambiar o simplemente regalar el producto
    private String precio;

    private Integer cantidad;

    private String idUsuario;

    public Producto (ProductoJpa productoJpa){
        if(productoJpa == null) return;

        this.setIdProducto(productoJpa.getIdProducto());
        this.setNombre(productoJpa.getNombre());
        this.setDescripcion(productoJpa.getDescripcion());
        this.setEstado(productoJpa.getEstado());
        this.setImagen(productoJpa.getImagen());
        this.setPrecio(productoJpa.getPrecio());
        this.setCantidad(productoJpa.getCantidad());
        this.setIdUsuario(productoJpa.getIdUsuario());
    }



}
