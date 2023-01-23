package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import com.project.trashure.transaccion.application.port.CreateTransaccionPort;
import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.infrastructure.repository.port.FindTransaccionPort;
import com.project.trashure.transaccion.infrastructure.repository.port.SaveTransaccionPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import com.project.trashure.usuario.infrastructure.repository.port.SaveUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/") //apunta a la raíz
public class UsuarioPrincipalController {

    //MIRAR ESTO: CREO QUE NO LO NECESITO PORQUE NO TENGO CARRITO > COMPRA
    // List<DetalleTransaccion> detalleTransaccionList = new ArrayList<DetalleTransaccion>();

    //MIRAR ESTO: LO QUE SÍ NECESITO ES UNA LISTA DE LOS PRODUCTOS FAVORITOS
    List<Producto> listaFavoritos = new ArrayList<Producto>();

    //MIRAR ESTO: ES POSIBLE QUE SÍ LO NECESITE CUANDO AÑADA EL BOTÓN DE COMPRAR EN EL DETALLE DE UN PRODUCTO
    //Transaccion transaccion = new Transaccion();

    private FindProductoPort findProductoPort;
    private FindUsuarioPort findUsuarioPort;

    private FindTransaccionPort findTransaccionPort;

    private SaveProductoPort saveProductoPort;

    private SaveTransaccionPort saveTransaccionPort;

    private CreateTransaccionPort createTransaccionPort;

    private SaveUsuarioPort saveUsuarioPort;

    //Este método retorna a la vista de la página principal del usuario

    @GetMapping("")
    public String principal(Model model, HttpSession httpSession) {
        List<Producto> productoList = findProductoPort.findAll();
        model.addAttribute("productoList", productoList);
        //Hay que enviar la sesión a la vista de la página principal para saber si el usuario está logueado o no
        if (httpSession.getAttribute("idUsuario") != null) {
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        } else {
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario"));
        }
        return "usuario/principal";
    }

    //Método que redirige a la vista de detalle del producto cuando se hace clic
    //sobre el botón de detalle desde la vista principal del usuario
    //como parámetro se le pasa el idProducto del producto que se quiere ver en detalle
    @GetMapping("detalleProducto/{idProducto}")
    public String detalleProducto(@PathVariable Integer idProducto, Model model, HttpSession httpSession) throws Exception {
        Producto producto = findProductoPort.findById(idProducto);
        model.addAttribute("producto", producto);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        //nos retorna a la vista de detalle del producto
        return "usuario/detalle_producto";
    }

    //Método para añadir un producto a favoritos, como parámetro le llega el id del producto
    //En vez de @PathVariable usamos @RequestParam ?????????? MIRAR ESTO
    @PostMapping("/annadirFavorito/{idProducto}")
    public String annadirFavorito(@RequestParam Integer idProducto, Model model, HttpSession httpSession) throws Exception {
        Producto producto = findProductoPort.findById(idProducto);

        //Se define un boolean que es true si en la lista de favoritos ya existe un match con el idProducto que
        //viene por parámetro y false si no está en la lista
        boolean yaEsFavorito = listaFavoritos.stream().anyMatch(producto1 -> String.valueOf(producto1.getIdProducto()).equals(String.valueOf(producto.getIdProducto())));

        //en caso de que no esté ese idProducto en la lista de favoritos, entonces se añade
        if (!yaEsFavorito) {
            //Este producto se añade a la lista de favoritos
            listaFavoritos.add(producto);
        }
        //si ya está añadido ese producto a la lista, no se vuelve a añadir

        //se envía la lista a la vista
        model.addAttribute("listaFavoritos", listaFavoritos);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        //Hace return hacia la vista de favoritos dentro de usuario
        return "usuario/favoritos";
    }

    //Método para eliminar un producto de la lista de favoritos
    @GetMapping("/deleteFavorito/{idProducto}")
    public String deleteFavorito(@PathVariable Integer idProducto, Model model) throws Exception {
        Producto productoToDelete = findProductoPort.findById(idProducto);

        for (Producto p : listaFavoritos) {
            String id = String.valueOf(p.getIdProducto());
            if (id.equals(idProducto)) {
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
    public String getFavoritos(Model model,HttpSession httpSession) {
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        model.addAttribute("listaFavoritos", listaFavoritos);
        //Hace return hacia la vista de favoritos dentro de usuario
        return "/usuario/favoritos";

    }

    //Método que redirige a la vista de resumen_compra
    @GetMapping("/resumenCompra/{idProducto}")
    public String resumenCompra(@RequestParam Integer idProducto, Model model, HttpSession httpSession) throws Exception {
        Producto producto = findProductoPort.findById(idProducto);
        String idUsuario = httpSession.getAttribute("idUsuario").toString();
        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        Usuario usuarioActual = findUsuarioPort.findById(idUsuarioInt);
        model.addAttribute("producto", producto);
        model.addAttribute("usuario", usuarioActual);
        return "usuario/resumen_compra";
    }

    //MIRAR ESTO V 33 IS THIS EVEN FINISHED??
    @PostMapping("/generarTransaccion/{idProducto}")
    public String generarTransaccion(@RequestParam Integer idProducto, Model model, HttpSession httpSession) throws Exception {
        String idUsuario = httpSession.getAttribute("idUsuario").toString();
        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        Usuario usuarioActual = findUsuarioPort.findById(idUsuarioInt);

        Producto producto = findProductoPort.findById(idProducto);

        if(producto.getIdUsuario().equals(idUsuarioInt)){
            throw new Exception ("No puedes comprar este objeto puesto que tú eres el propietario.");
        }

        producto.setDisponibilidad("No disponible");
        Integer idVendedor = producto.getIdUsuario();
        saveProductoPort.save(producto);


        Transaccion transaccionCompra = new Transaccion();
        transaccionCompra.setFechaTransaccion(new Date());
        transaccionCompra.setIdComprador(idUsuarioInt);
        transaccionCompra.setIdVendedor(idVendedor);
        transaccionCompra.setEstado("Pendiente");
        transaccionCompra.setProducto(producto);
        Transaccion transaccionCreated = createTransaccionPort.create(transaccionCompra);

        if (usuarioActual.getListaCompras() == null) {
            List<Transaccion> listaCompras = new ArrayList<>();
            listaCompras.add(transaccionCreated);
            usuarioActual.setListaCompras(listaCompras);
        } else {
            usuarioActual.getListaCompras().add(transaccionCreated);
        }
        saveUsuarioPort.save(usuarioActual);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());

        return "usuario/compras";


    }

    //Método para buscar un producto en la barra de búsqueda de la pantalla principal
    @PostMapping("buscarProducto")
    public String buscarProducto(@RequestParam String textoBusqueda, Model model) {
        System.out.println("HOLA");
        //Se va a recoger en una lista todos los productos que contengan en el nombre el textBusqueda
        //se cogen todos los productos de la base de datos y luego se filtran por el nombre
        List<Producto> productoBusquedaList = findProductoPort.findAll().stream().filter
                (x -> x.getNombre().toLowerCase().contains((textoBusqueda).toLowerCase())).collect(Collectors.toList() );

        System.out.println("tamaño lista " + productoBusquedaList.size());

        //Ahora hay que enviar la lista hacia la vista con model
        model.addAttribute("productoList", productoBusquedaList);

        //redirige a la vista
        return "usuario/principal";
    }

    //Método que redirige a la vista de compras del usuario logueado
    @GetMapping("/getCompras")
    public String historialCompras(Model model, HttpSession httpSession) throws Exception {
        String idUsuario = httpSession.getAttribute("idUsuario").toString();

        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        if (idUsuario != null) {
            //recuperamos la lista de transacciones realizadas como comprador por parte del usuario
            List<Transaccion> historialCompras = findTransaccionPort.findAllByIdComprador(idUsuarioInt);
            model.addAttribute("historialCompras", historialCompras);
        }

        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());

        //Redirige a la vista de compras del usuario
        return "usuario/compras";
    }

    //Método que redirige a la vista de ventas del usuario logueado ?????
    @GetMapping("/getVentas")
    public String historialVentas(Model model, HttpSession httpSession) {
        String idUsuario = httpSession.getAttribute("idUsuario").toString();

        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        if (idUsuario != null) {
            //recuperamos la lista de transacciones realizadas como vendedor por parte del usuario
            List<Transaccion> historialVentas = findTransaccionPort.findAllByIdVendedor(idUsuarioInt);
            model.addAttribute("historialVentas", historialVentas);
        }

        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());

        //Redirige a la vista de ventas del usuario
        return "usuario/ventas";
    }

    @GetMapping("getDetalleCompra/{idTransaccion}")
    public String getDetalleCompra(@PathVariable Integer idTransaccion, Model model, HttpSession httpSession) throws Exception {
        Transaccion compra = findTransaccionPort.findById(idTransaccion);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());

        model.addAttribute("compra", idTransaccion);
        return "usuario/detalle_compra";

    }

    @GetMapping("getDetalleVenta/{idTransaccion}")
    public String getDetalleVenta(@PathVariable Integer idTransaccion, Model model, HttpSession httpSession) {
        model.addAttribute("idTransaccion", idTransaccion);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        return "usuario/detalle_venta";

    }

    //Método que redirige a la vista de los productos de un usuario
    @GetMapping("/getProductos")
    public String getProductos(Model model, HttpSession httpSession) throws Exception {
        Usuario usuario = new Usuario();
        if (httpSession.getAttribute("idUsuario") != null) {
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
            String idUsuario = httpSession.getAttribute("idUsuario").toString();
            Integer id = Integer.parseInt(idUsuario);
            usuario = findUsuarioPort.findById(id);
        } else {
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario"));
        }

        List<Producto> listaProductos = new ArrayList<>();
        listaProductos = findProductoPort.findAllByPropietario(usuario);
        model.addAttribute("listaProductos", listaProductos);
        //Hace return hacia la vista de favoritos dentro de usuario
        return "usuario/productos";

    }

    @PostMapping("rechazar/{idTransaccion}")
    public String rechazarTransaccion(@PathVariable Integer idTransaccion, Model model, HttpSession httpSession) throws Exception {
        Transaccion transaccion = findTransaccionPort.findById(idTransaccion);
        transaccion.setEstado("RECHAZADA");
        saveTransaccionPort.save(transaccion);
        Producto producto = findProductoPort.findById(transaccion.getProducto().getIdProducto());
        producto.setDisponibilidad("Disponible");
        saveProductoPort.save(producto);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        return "usuario/ventas";
    }

    @PostMapping("aceptar/{idTransaccion}")
    public String aceptarTransaccion(@PathVariable Integer idTransaccion, Model model, HttpSession httpSession) throws Exception {
        Transaccion transaccion = findTransaccionPort.findById(idTransaccion);
        transaccion.setEstado("ACEPTADA");
        saveTransaccionPort.save(transaccion);
        Producto producto = findProductoPort.findById(transaccion.getProducto().getIdProducto());
        producto.setDisponibilidad("Vendido");
        saveProductoPort.save(producto);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        return "usuario/ventas";
    }

    @GetMapping("visitarPefil/{idUsuario}")
    public String visitarPerfil(@PathVariable Integer idUsuario, Model model, HttpSession httpSession) throws Exception {
        Usuario usuario = findUsuarioPort.findById(idUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        return "usuario/ver_perfil";
    }


}
