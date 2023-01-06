package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.application.port.CreateProductoPort;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.SaveUsuarioRepository;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import com.project.trashure.usuario.infrastructure.repository.port.SaveUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v0/productos")
@AllArgsConstructor
public class CreateProductoController {

    private FindUsuarioPort findUsuarioPort;

    private SaveUsuarioPort saveUsuarioPort;
    private CreateProductoPort createProductoPort;
    //se crea un objeto de la clase Logger para ir haciendo test y pruebas
    private final Logger LOGGER = LoggerFactory.getLogger(CreateProductoController.class);

    //Este método devuelve un String porque redirecciona a la vista mostrar
    @PostMapping("/createProducto")
    public String createProducto(Producto producto, HttpSession httpsession) throws Exception {


        String idUsuario = httpsession.getAttribute("idUsuario").toString();
        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        Usuario usuario = findUsuarioPort.findById(idUsuarioInt);
        usuario.getProductosSubidos().add(producto);
        saveUsuarioPort.save(usuario);

        //MIRAR ESTO:
        //al crear un producto, se debería guardar con el id del usuario que lo sube
        //cómo hacer esto??


        //producto.setIdUsuario(idUsuario);
        producto.setPropietario(usuario);
        producto.setIdUsuario(idUsuarioInt);

        //como el producto está siendo creado, su disponibilidad es disponible
        producto.setDisponibilidad("Disponible");
        //video 11 minuto 3

        createProductoPort.create(producto);


        //Redirect porque es una petición a GetProductoController
        //es decir, llama al método mostrar del controlador
        return "redirect:/api/v0/productos/getProductos";
    }
}
