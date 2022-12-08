package com.project.trashure.producto.infrastructure.controller.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoInputDTO {

    private String idProducto;

    private String nombre;

    private String descripcion;

    private String estado;

    private String imagen;
    private String precio;

    private Integer cantidad;

}
