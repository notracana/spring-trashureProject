package com.project.trashure.usuario.infrastructure;

import com.project.trashure.detalletransaccion.domain.DetalleTransaccion;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.transaccion.domain.Transaccion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/") //apunta a la raíz
public class UsuarioPrincipalController {

    //MIRAR ESTO: CREO QUE NO LO NECESITO PORQUE NO TENGO CARRITO > COMPRA
    List<DetalleTransaccion> detalleTransaccionList = new ArrayList<DetalleTransaccion>();

    //MIRAR ESTO: LO QUE SÍ NECESITO ES UNA LISTA DE LOS PRODUCTOS FAVORITOS
    List<Producto> listaFavoritos = new ArrayList<Producto>();

    //MIRAR ESTO: ES POSIBLE QUE SÍ LO NECESITE CUANDO AÑADA EL BOTÓN DE COMPRAR EN EL DETALLE DE UN PRODUCTO
    Transaccion transaccion = new Transaccion();

    private FindProductoPort findProductoPort;
    //Este método retorna a la vista de la página principal del usuario

    @GetMapping("")
    public String principal(Model model){
        List<Producto> productoList = findProductoPort.findAll();
        model.addAttribute("productos", productoList);
        return "usuario/principal";
    }

    //Método que redirige a la vista de detalle del producto cuando se hace clic
    //sobre el botón de detalle desde la vista principal del usuario
    //como parámetro se le pasa el idProducto del producto que se quiere ver en detalle
    @GetMapping("detalleProducto/{idProducto}")
    public String detalleProducto(@PathVariable String idProducto, Model model) throws Exception {
        Producto producto = findProductoPort.findById(idProducto);
        model.addAttribute("producto", producto);
        //nos retorna a la vista de detalle del producto
        return "usuario/detalle_producto";
    }

    //Método para añadir un producto a favoritos, como parámetro le llega el id del producto
    //En vez de @PathVariable usamos @RequestParam ?????????? MIRAR ESTO
    @PostMapping("/annadirFavorito/{idProducto}")
    public String annadirFavorito(@RequestParam String idProducto, Model model) throws Exception {
        Producto producto = findProductoPort.findById(idProducto);

        //Se define un boolean que es true si en la lista de favoritos ya existe un match con el idProducto que
        //viene por parámetro y false si no está en la lista
        boolean yaEsFavorito = listaFavoritos.stream().anyMatch(producto1 -> producto1.getIdProducto().equals(producto.getIdProducto()));

        //en caso de que no esté ese idProducto en la lista de favoritos, entonces se añade
        if(!yaEsFavorito){
            //Este producto se añade a la lista de favoritos
            listaFavoritos.add(producto);
        }
        //si ya está añadido ese producto a la lista, no se vuelve a añadir

        //se envía la lista a la vista
        model.addAttribute("listaFavoritos", listaFavoritos);

        //Hace return hacia la vista de favoritos dentro de usuario
        return "usuario/favoritos";
    }

    //Método para eliminar un producto de la lista de favoritos
    @GetMapping("/deleteFavorito/{idProducto}")
    public String deleteFavorito(@PathVariable String idProducto, Model model) throws Exception {
        Producto productoToDelete = findProductoPort.findById(idProducto);

        for(Producto p : listaFavoritos){
            if(p.getIdProducto().equals(idProducto)){
                //se obtiene el índice del producto a borrar dentro de la lista
                //o directamente se borra ese prodcuto de la lista
                listaFavoritos.remove(p);
            }
        }
        //Se vuelve a enviar la lista de favoritos (sin el producto eliminado) a la vista
        model.addAttribute("listaFavoritos", listaFavoritos);
        //Hace return hacia la vista de favoritos dentro de usuario
        return "usuario/favoritos";

    }


    //Método que redirige a la vista de favoritos desde cualquier parte de la app
    @GetMapping("/getFavoritos")
    public String getFavoritos (Model model){
        model.addAttribute("listaFavoritos", listaFavoritos);
        //Hace return hacia la vista de favoritos dentro de usuario
        return "/usuario/favoritos";

    }
}
