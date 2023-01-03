package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.DeleteImagenProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.DeleteProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v0/productos")
public class DeleteProductoController {
    private DeleteProductoPort deleteProductoPort;

    private FindProductoPort findProductoPort;

    private DeleteImagenProductoPort deleteImagenProductoPort;



    @DeleteMapping("{idProducto}")
    @Transactional(rollbackFor = Exception.class)
    public void delete(@PathVariable("idProducto") Integer idProducto){
        deleteProductoPort.deleteById(idProducto);
    }

    //endpoint para redirigir a la vista de todos los productos una vez borrado un producto
    @GetMapping("/delete/{idProducto}")
    public String deleteProducto(@PathVariable Integer idProducto) throws Exception {

        //antes de borrar el producto, borramos la imagen
        Producto producto = new Producto();
        producto = findProductoPort.findById(idProducto);

        //si la imagen del producto no es la imagen por defecto, se borra
        if(!producto.getImagen().equals("No_imagen_available.png")){
            deleteImagenProductoPort.deleteImagenProducto(producto.getImagen());
        }

        deleteProductoPort.deleteById(idProducto);
        return "redirect:/producto";
    }
}
