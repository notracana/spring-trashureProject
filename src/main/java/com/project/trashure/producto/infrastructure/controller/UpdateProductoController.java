package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.application.port.UpdateProductoPort;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.controller.dto.input.ProductoInputDTO;
import com.project.trashure.producto.infrastructure.controller.dto.output.ProductoOutputDTO;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v0/productos")
public class UpdateProductoController {

    private FindProductoPort findProductoPort;
    private UpdateProductoPort updateProductoPort;

    @PutMapping("{idProducto}")
    @Transactional(rollbackFor = Exception.class)
    public ProductoOutputDTO update(
            @PathVariable("idProducto") String idProducto,
            @RequestBody ProductoInputDTO productoInputDTO) throws Exception {

        Producto producto = findProductoPort.findById(idProducto);
        Producto productoUpdated = updateProductoPort.update(idProducto, productoInputDTO.getNombre(), productoInputDTO.getDescripcion(), productoInputDTO.getEstado(),
                productoInputDTO.getImagen(), productoInputDTO.getPrecio(), productoInputDTO.getCantidad());
        return new ProductoOutputDTO(productoUpdated);

    }
}
