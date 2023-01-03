package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.application.port.CreateImagenProductoPort;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.jpa.ProductoRepositoryJpa;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("api/v0/productos")
@AllArgsConstructor
public class SaveProductoController {
    private SaveProductoPort saveProductoPort;

    private CreateImagenProductoPort createImagenProductoPort;

    private FindProductoPort findProductoPort;

    private FindUsuarioPort findUsuarioPort;

    @PostMapping("/saveProducto")
    public String saveProducto(
            Producto producto,
            @RequestParam ("imgProducto") MultipartFile imgFile,
            HttpSession httpSession) throws Exception {
        //El nombre del imgFile entre paréntesis es el nombre que se le da en la vista crear.html
        //el noombre del campo en el formulario --> víd 16 min 4.50

        String idUsuario = httpSession.getAttribute("idUsuario").toString();
       //Al producto se le setea el id del usuario logueado
        //producto.setIdUsuario(idUsuario);

        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        Usuario usuarioLogged = findUsuarioPort.findById(idUsuarioInt);
        producto.setPropietario(usuarioLogged);

        //Para cargar una imagen pueden darse varios casos
        //si el producto se está creando...
        if(producto.getIdProducto() == null){
             //Se guarda el nombre de la imagen en una variable String
            //la imgFile es un objeto de tipo MultipartFile que entra como RequestParam
            String nombreImg = createImagenProductoPort.saveImagen(imgFile);
            //ahora se setea ese nombre en el campo imagen de Producto
            producto.setImagen(nombreImg);
        }
        //en el caso de que Producto.getIdProducto no sea null porque estemos modificando un producto
        else{
            //puede que la imagen sea la misma
            if(imgFile.isEmpty()){
                Producto producto1 = findProductoPort.findById(producto.getIdProducto());
                producto.setImagen(producto1.getImagen());
            }
            //o puede que sea una imagen nueva
            if(!imgFile.isEmpty()){
                //Cuando modificamos el producto y también la imagen..
                String nombreImg = createImagenProductoPort.saveImagen(imgFile);
                //ahora se setea ese nombre en el campo imagen de Producto
                producto.setImagen(nombreImg);

            }

        }
        saveProductoPort.save(producto);
        return "redirect:/producto";

    }

}
