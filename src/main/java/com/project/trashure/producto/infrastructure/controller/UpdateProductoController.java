package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.error.ErrorPropio;
import com.project.trashure.producto.application.port.CreateImagenProductoPort;
import com.project.trashure.producto.application.port.UpdateProductoPort;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.controller.dto.input.ProductoInputDTO;
import com.project.trashure.producto.infrastructure.controller.dto.output.ProductoOutputDTO;
import com.project.trashure.producto.infrastructure.repository.port.DeleteImagenProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Controller
@RequestMapping("api/v0/productos")
public class UpdateProductoController {

    private FindProductoPort findProductoPort;
    private UpdateProductoPort updateProductoPort;

    private CreateImagenProductoPort createImagenProductoPort;

    private DeleteImagenProductoPort deleteImagenProductoPort;

    @PutMapping("{idProducto}")
    @Transactional(rollbackFor = Exception.class)
    public ProductoOutputDTO update(
            @PathVariable("idProducto") Integer idProducto,
            @RequestBody ProductoInputDTO productoInputDTO) throws Exception {

        Producto producto = findProductoPort.findById(idProducto);
        Producto productoUpdated = updateProductoPort.update(idProducto, productoInputDTO.getNombre(), productoInputDTO.getDescripcion(),
                productoInputDTO.getEstado(), productoInputDTO.getCategoria(), productoInputDTO.getImagen());
        return new ProductoOutputDTO(productoUpdated);

    }


    //Este método devuelve un String porque redirecciona a la vista editar.html
    //Además del id, como parámetro se le pasa un objeto de tipo Model, que lleva datos desde el backend a la vista
    //es decir, traslada el producto encontrado en la bdd a la vista
    @GetMapping("/editarProducto/{idProducto}")
    public String editarProducto(
            @PathVariable Integer idProducto, Model model, HttpSession httpSession) throws Exception {


        System.out.println("id producto en editarproducto/idproducto " + idProducto);
        //Se guarda el producto encontrado con el id en un objeto de tipo Producto
        Producto producto = findProductoPort.findById(idProducto);

        //MIRAR ESTO:
        //aQUÍ podríamos usar un Optional
        //  Producto producto = new Producto();
        // Optinal <Producto> optProducto = findProductoPort.findById(idProducto);
        //producto = optProducto.get();

        //
        model.addAttribute("producto", producto); //con esto, se envía a la plantillña de editar el
        //objeto buscado

        model.addAttribute("idProducto", producto.getIdProducto());
        System.out.println("producto que se envia con el model " + producto.getIdProducto());


        if(httpSession.getAttribute("idUsuario").toString() != null){
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());

        }
        else{
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario"));
        }

        //VALIDACION NECESARIA
        if(!producto.getIdUsuario().toString().contentEquals(httpSession.getAttribute("idUsuario").toString())){
            System.out.println("producto.getIdUsuario().toString() " + producto.getIdUsuario().toString());
            System.out.println("httpSession.getAttribute(\"idUsuario\").toString())" + httpSession.getAttribute("idUsuario").toString());
            ErrorPropio errorPropio = new ErrorPropio();
            errorPropio.setTexto("No puedes acceder a la edición de un producto del que no eres propietario.");
            model.addAttribute("error", errorPropio);
            return "usuario/modal_error";
        }

        //MIRAR ESTO:
        //EXPLICACIÓN DE CÓMO SE LLEVA DEL MÉTODO EDITAR PRODUCTO A LA VISTA DE EDITAR DE MOSTRAR



        //Redirect porque es una petición a GetProductoController
        //es decir, llama al método mostrar del controlador
        return "producto/editar";
    }

    //MIRAR ESTO:
    //método update que redirecciona al final a la vista de mostrar todos los productos
    //vídeo 13 minuto 17
    //método para que actualice

    //22/12 cogemos parte de saveProductoController para traernos la actualizacion de la img del producto vid 17
    @PostMapping("/update")
    public String update(Producto producto, Model model,
                         @RequestParam ("imgProducto") MultipartFile imgFile) throws Exception {

        String nombre =  producto.getNombre();

        System.out.println("nombre " + nombre);

        String descripcion = producto.getDescripcion();
        System.out.println("descripcion " + descripcion);
        Integer intIdProducto = producto.getIdProducto();

        System.out.println("id " + intIdProducto);

        //String idProducto =  model.getAttribute("idProducto").toString();
        //System.out.println("id " + idProducto);


        //Integer idProductoInt = Integer.parseInt(idProducto);


        //Producto producto2 = model.getAttribute("producto");
        //System.out.println("id producto que viene por parametro " + idProductoInt);

        Producto producto1 = findProductoPort.findById(intIdProducto);

        //System.out.println("id producto que se va a editar " + producto1.getIdProducto());


        //puede que la imagen sea la misma
        if(imgFile.isEmpty()){
            producto.setImagen(producto1.getImagen());
        }
        //o puede que sea una imagen nueva
        if(!imgFile.isEmpty()){
            //Cuando modificamos el producto y también la imagen..
            //si la imagen del producto no es la imagen por defecto, se borra
            if(!producto1.getImagen().equals("No_imagen_available.png")){
                deleteImagenProductoPort.deleteImagenProducto(producto1.getImagen());
            }

            String nombreImg = createImagenProductoPort.saveImagen(imgFile);
            //ahora se setea ese nombre en el campo imagen de Producto
            producto.setImagen(nombreImg);

        }
        //mirar esto
        //no me convence. no debería ser producto1.setIdProducto(producto...)
        producto.setIdProducto(producto1.getIdProducto());

        //MIRAR ESTO
        //EL UPDATE NOP ME GFUSTA. COMPARAR CON EL SUYO EN MIN 13.50 VIDEO 18
        updateProductoPort.update(producto.getIdProducto(), producto.getNombre(), producto.getDescripcion()
        , producto.getEstado(), producto.getCategoria(), producto.getImagen());
        return "redirect:/getProductos";
    }
}
