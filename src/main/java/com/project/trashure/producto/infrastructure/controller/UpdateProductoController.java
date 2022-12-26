package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.application.port.CreateImagenProductoPort;
import com.project.trashure.producto.application.port.UpdateProductoPort;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.controller.dto.input.ProductoInputDTO;
import com.project.trashure.producto.infrastructure.controller.dto.output.ProductoOutputDTO;
import com.project.trashure.producto.infrastructure.repository.port.DeleteImagenProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("api/v0/productos")
public class UpdateProductoController {

    private FindProductoPort findProductoPort;
    private UpdateProductoPort updateProductoPort;

    private CreateImagenProductoPort createImagenProductoPort;

    private DeleteImagenProductoPort deleteImagenProductoPort;

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


    //Este método devuelve un String porque redirecciona a la vista editar.html
    //Además del id, como parámetro se le pasa un objeto de tipo Model, que lleva datos desde el backend a la vista
    //es decir, traslada el producto encontrado en la bdd a la vista
    @GetMapping("/editarProducto/{idProducto}")
    public String editarProducto(
            @PathVariable String idProducto, Model model) throws Exception {


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



        //MIRAR ESTO:
        //EXPLICACIÓN DE CÓMO SE LLEVA DEL MÉTODO EDITAR PRODUCTO A LA VISTA DE EDITAR DE MOSTRAR



        //Redirect porque es una petición a GetProductoController
        //es decir, llama al método mostrar del controlador
        return "redirect:/producto/editar";
    }

    //MIRAR ESTO:
    //método update que redirecciona al final a la vista de mostrar todos los productos
    //vídeo 13 minuto 17
    //método para que actualice

    //22/12 cogemos parte de saveProductoController para traernos la actualizacion de la img del producto vid 17
    @PostMapping("/update")
    public String update(Producto producto, @RequestParam ("imgProducto") MultipartFile imgFile) throws Exception {
        Producto producto1 = findProductoPort.findById(producto.getIdProducto());


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
        , producto.getEstado(), producto.getImagen(), producto.getPrecio(), producto.getCantidad());
        return "redirect:/producto/mostrar";
    }
}
