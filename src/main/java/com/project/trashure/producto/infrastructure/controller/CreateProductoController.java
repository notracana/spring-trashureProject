package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.application.port.CreateImagenProductoPort;
import com.project.trashure.producto.application.port.CreateProductoPort;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.SaveUsuarioRepository;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import com.project.trashure.usuario.infrastructure.repository.port.SaveUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v0/productos")
@AllArgsConstructor
public class CreateProductoController {

    private FindUsuarioPort findUsuarioPort;

    private SaveUsuarioPort saveUsuarioPort;
    private CreateProductoPort createProductoPort;

    private FindProductoPort findProductoPort;

    private CreateImagenProductoPort createImagenProductoPort;
    //se crea un objeto de la clase Logger para ir haciendo test y pruebas
    private final Logger LOGGER = LoggerFactory.getLogger(CreateProductoController.class);

    //Este método devuelve un String porque redirecciona a la vista mostrar
    @PostMapping("/createProducto")
    public String createProducto(Producto producto, HttpSession httpsession, @RequestParam("imgProducto") MultipartFile imgFile) throws Exception {


        String idUsuario = httpsession.getAttribute("idUsuario").toString();
        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        Usuario usuario = findUsuarioPort.findById(idUsuarioInt);
        System.out.println("el usuario es " + usuario.getIdUsuario());
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

        //Para cargar una imagen pueden darse varios casos
        //si el producto se está creando...
        if(producto.getIdProducto() == null){
            //Se guarda el nombre de la imagen en una variable String
            //la imgFile es un objeto de tipo MultipartFile que entra como RequestParam
            String nombreImg = createImagenProductoPort.saveImagen(imgFile);
            System.out.println("nombre foto " + nombreImg);

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
                System.out.println("nombre foto " + nombreImg);
                //ahora se setea ese nombre en el campo imagen de Producto
                producto.setImagen(nombreImg);

            }

        }

        Producto productoSaved = createProductoPort.create(producto);
        System.out.println("el producto tiene como usuario " +productoSaved.getIdUsuario() );


        //Redirect porque es una petición a GetProductoController
        //es decir, llama al método mostrar del controlador
        return "redirect:/";
    }
}
