package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.infrastructure.repository.port.DeleteProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v0/productos")
public class DeleteProductoController {
    private DeleteProductoPort deleteProductoPort;



    @DeleteMapping("{idProducto}")
    @Transactional(rollbackFor = Exception.class)
    public void delete(@PathVariable("idProducto") String idProducto){
        deleteProductoPort.deleteById(idProducto);
    }

    //endpoint para redirigir a la vista de todos los productos una vez borrado un producto
    @GetMapping("/delete/{idProducto}")
    public String deleteProducto(@PathVariable String idProducto){
        deleteProductoPort.deleteById(idProducto);
        return "redirect:/producto";
    }
}
