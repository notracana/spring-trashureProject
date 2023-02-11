package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.error.ErrorPropio;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    public String createProducto(Producto producto, Model model, HttpSession httpsession, @RequestParam("imgProducto") MultipartFile imgFile) throws Exception {

        String idUsuario = httpsession.getAttribute("idUsuario").toString();
        if(idUsuario == null){
            ErrorPropio e = new ErrorPropio();
            e.setTexto("Necesitas iniciar sesión para poder subir tus productos.");
            model.addAttribute("error", e);
            return "usuario/error_modal";
        }// throw new Exception("Necesitas iniciar sesión para poder subir tus productos.");
        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        Usuario usuario = findUsuarioPort.findById(idUsuarioInt);


        System.out.println("usuario id " + usuario.getIdUsuario());

        //MIRAR ESTO:
        //al crear un producto, se debería guardar con el id del usuario que lo sube
        //cómo hacer esto??


        //producto.setIdUsuario(idUsuario);
        //producto.setPropietario(usuario);
        producto.setIdUsuario(idUsuarioInt);

        producto.setPropietario(usuario);


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


        Producto productoCreated = createProductoPort.create(producto);
        System.out.println("el producto tiene como usuario " + productoCreated.getIdUsuario() );

        System.out.println("el usuario es " + usuario.getIdUsuario());

        /*
        System.out.println("lista de productos del usuario antes de añadir el nuevo ");
        for(Producto p : usuario.getProductosSubidos()){
            System.out.println("producto " + p.getIdProducto());
        }
        System.out.println("añadimos el nuevo producto a su lista");
        usuario.getProductosSubidos().add(productoCreated);
        System.out.println("lista de productos del usuario después de añadir el nuevo ");
        for(Producto p : usuario.getProductosSubidos()){
            System.out.println("producto " + p.getIdProducto());
        }
        System.out.println("salvamos el usuario con su lista modificada");
        Usuario user = saveUsuarioPort.save(usuario);

        System.out.println("lista de productos subidos del usuario una vez salvado");
        for(Producto p : user.getProductosSubidos()){
            System.out.println("producto " + p.getIdProducto());
        }
        //Redirect porque es una petición a GetProductoController
        //es decir, llama al método mostrar del controlador

         */
        return "redirect:/";
    }

    @PostMapping("/annadirFavorito/{idProducto}")
    public String annadirFavorito(@RequestParam Integer idProducto, Model model, HttpSession httpSession) throws Exception {
        String idUsuario = httpSession.getAttribute("idUsuario").toString();
        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        Usuario usuarioActual = findUsuarioPort.findById(idUsuarioInt);
        List<Integer> listaFavoritos = usuarioActual.getIdProductosFavoritos();
        if(listaFavoritos == null) listaFavoritos = new ArrayList<>();

        //Se define un boolean que es true si en la lista de favoritos ya existe un match con el parámetro '
        // 'idProducto' y false si no está en la lista

        boolean yaEsFavorito = false;
        if(listaFavoritos!=null && listaFavoritos.size()!=0) {
            yaEsFavorito = listaFavoritos.stream().anyMatch(x -> String.valueOf(x).equals(String.valueOf(idProducto)));
        }

        //en caso de que no esté ese idProducto en la lista de favoritos, se añade y se guardan los cambios en la lista del usuario
        if (!yaEsFavorito) {
            listaFavoritos.add(idProducto);
            usuarioActual.setIdProductosFavoritos(listaFavoritos);
            saveUsuarioPort.save(usuarioActual);
        }
        //si ya está añadido ese producto a la lista, no se vuelve a añadir

        //Se recuperan en una lista todos los productos
        List<Producto> listaProductos = new ArrayList<>();
        for(Integer id :  listaFavoritos){
            Producto p =  findProductoPort.findById(id);
            listaProductos.add(p);
        }

        //se envía la lista a la vista
        model.addAttribute("listaFavoritos", listaProductos);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        //Hace return hacia la vista de favoritos dentro de usuario

        return "redirect:/getFavoritos";
    }

}
