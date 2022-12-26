package com.project.trashure.producto.infrastructure.repository.port;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface DeleteImagenProductoPort {
    public void deleteImagenProducto(String nombreImagen);
}
