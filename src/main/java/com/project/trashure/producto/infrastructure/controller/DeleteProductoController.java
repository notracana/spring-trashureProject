package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.error.ErrorPropio;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.DeleteImagenProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.DeleteProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("api/v0/productos")
public class DeleteProductoController {
    private DeleteProductoPort deleteProductoPort;

    private FindProductoPort findProductoPort;

    private DeleteImagenProductoPort deleteImagenProductoPort;


    @DeleteMapping("{idProducto}")
    @Transactional(rollbackFor = Exception.class)
    public void delete(@PathVariable("idProducto") Integer idProducto) {
        deleteProductoPort.deleteById(idProducto);
    }

    //endpoint para redirigir a la vista de todos los productos una vez borrado un producto
    @GetMapping("/delete/{idProducto}")
    public String deleteProducto(@PathVariable Integer idProducto, HttpSession httpSession, Model model) throws Exception {

        if (httpSession.getAttribute("idUsuario") == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Petición no autorizada.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        //antes de borrar el producto, borramos la imagen
        Producto producto = new Producto();
        producto = findProductoPort.findById(idProducto);
        if (producto == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("El producto no existe.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }

        if (httpSession.getAttribute("idUsuario").toString() != producto.getIdUsuario().toString()) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Petición no autorizada. Parece que no eres el propietario.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }

        //si la imagen del producto no es la imagen por defecto, se borra
        if (!producto.getImagen().equals("No_imagen_available.png")) {
            deleteImagenProductoPort.deleteImagenProducto(producto.getImagen());
        }

        deleteProductoPort.deleteById(idProducto);
        return "usuario/productos";
    }

    @GetMapping("/del/{idProducto}")
    public String delProducto(@PathVariable Integer idProducto, HttpSession httpSession, Model model) throws Exception {

        if (httpSession.getAttribute("idAdmin") == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Petición no autorizada.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        //antes de borrar el producto, borramos la imagen
        Producto producto = new Producto();
        producto = findProductoPort.findById(idProducto);

        if (producto == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("El producto no existe.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }

        //si la imagen del producto no es la imagen por defecto, se borra
        if (!producto.getImagen().equals("No_imagen_available.png")) {
            deleteImagenProductoPort.deleteImagenProducto(producto.getImagen());
        }

        deleteProductoPort.deleteById(idProducto);
        return "redirect:/api/v0/productos/getProductos";
    }

}
