package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.controller.dto.output.ProductoOutputDTO;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v0/productos")
public class FindProductoController {
    private FindProductoPort findProductoPort;

    @GetMapping("{idProducto}")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public ProductoOutputDTO findById(@PathVariable("idProducto") String idProducto) throws Exception {
        Producto producto = findProductoPort.findById(idProducto);
        return new ProductoOutputDTO(producto);
    }
}
