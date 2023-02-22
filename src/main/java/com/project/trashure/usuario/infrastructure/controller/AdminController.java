package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.email.Email;
import com.project.trashure.error.ErrorPropio;
import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.infrastructure.repository.port.FindTransaccionPort;
import com.project.trashure.usuario.application.port.CreateUsuarioPort;
import com.project.trashure.usuario.application.port.UpdateUsuarioPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("api/v0/admin")
public class AdminController {

    private FindProductoPort findProductoPort;

    private FindUsuarioPort findUsuarioPort;

    private FindTransaccionPort findTransaccionPort;

    private CreateUsuarioPort createUsuarioPort;

    private UpdateUsuarioPort updateUsuarioPort;

    private JavaMailSender javaMailSender;


    @GetMapping("/principal")
    public String principal(Model model, HttpSession httpSession) {
        //el método principal recibe como parámetro un objeto Model
        //para enviar los productos a la vista principal

        if (httpSession.getAttribute("idAdmin") == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Ups, parece que te has perdido.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        if (httpSession.getAttribute("idAdmin") != null) {
            model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());
        }
        //se crea una lista de los productos y se recuperan todos los guardados
        List<Producto> productoList = findProductoPort.findAll();
        //a model, addAttribute con parámetros el nombre de la variable con lo que lo voy a enviar
        //y el contenido de la lista
        //de esta forma, al llamar a este método, se obtendrían los productos
        //y pasan a la vista principal
        model.addAttribute("productoList", productoList);

        //es necesario que en la vista principal.html para iterar sobre los cards a lo largo de la lista de productos
        return "admin/principal";
    }


    //Método para llevar a la vista de admin de Usuarios
    @GetMapping("/getUsuarios")
    public String getUsuarios(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("idAdmin") == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Ups, parece que te has perdido.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        if (httpSession.getAttribute("idAdmin") != null) {
            model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());
        }

        List<Usuario> usuarioList = findUsuarioPort.findAll();
        //Se envía la lista de usuarios a la vista
        model.addAttribute("listaUsuarios", usuarioList);

        //Redirige a la vista de los usuarios de Admin
        return "admin/usuarios";
    }


    //Método para llevar a la vista de admin de Transacciones
    @GetMapping("/getTransacciones")
    public String getTransacciones(Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("idAdmin") == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Ups, parece que te has perdido.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        if (httpSession.getAttribute("idAdmin") != null) {
            model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());
        }

        List<Transaccion> transaccionList = findTransaccionPort.findAll();
        //Se envía la lista de transacciones a la vista
        model.addAttribute("listaTransacciones", transaccionList);

        //Redirige a la vista de las transacciones de Admin
        return "admin/transacciones";
    }

    @PostMapping("registrarUsuario")
    public String registrarUsuario(Usuario usuario) throws Exception {

        //El usuario que llega como parámetro viene de los datos del formulario de la vista sign_up
        //hay que determinar que el usuario que se registra es de tipo normal, es decir, no es administrador

        usuario.setTipoUsuario("USER");

        //se crea el usuario con los datos aportados
        createUsuarioPort.create(usuario);
        //Tras guardar el nuevo usuario en la base de datos, redirige a la página principal de usuario
        return "redirect:/api/v0/admin/principal";
    }

    @PostMapping("updateUsuario")
    public String updateUsuario(Usuario usuario, Model model, HttpSession httpSession) throws Exception {
        System.out.println("heyy qué pasa makinasa");
        //Usuario usuario1 = findUsuarioPort.findById(usuario.getIdUsuario());


        System.out.println("usuario id parametro " + usuario.getIdUsuario());

        String id = httpSession.getAttribute("idUsuario").toString();
        Integer idInt = Integer.parseInt(id);
        Usuario usuarioHttp = findUsuarioPort.findById(idInt);

        System.out.println("usuario http id " + usuarioHttp.getIdUsuario());
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        //usuario.setIdUsuario(usuario1.getIdUsuario());

        //MIRAR ESTO
        //EL UPDATE NOP ME GFUSTA. COMPARAR CON EL SUYO EN MIN 13.50 VIDEO 18
        updateUsuarioPort.updateInfo(usuarioHttp.getIdUsuario(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getTelefono(),
                usuario.getDireccion(), usuario.getLocalidad());

        return "admin/usuarios";
    }


    //Método para buscar un producto en la barra de búsqueda de la pantalla principal
    @PostMapping("buscarProducto")
    public String buscarProducto(@RequestParam String textoBusqueda, Model model, HttpSession httpSession) {
        System.out.println("HOLA");
        if (httpSession.getAttribute("idAdmin") == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Ups, parece que te has perdido.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        if (httpSession.getAttribute("idAdmin") != null) {
            model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());
        }
        //Se va a recoger en una lista todos los productos que contengan en el nombre el textBusqueda
        //se cogen todos los productos de la base de datos y luego se filtran por el nombre
        List<Producto> productoBusquedaList = findProductoPort.findAll().stream().filter
                (x -> x.getNombre().toLowerCase().contains((textoBusqueda).toLowerCase())).collect(Collectors.toList());

        System.out.println("tamaño lista " + productoBusquedaList.size());

        //Ahora hay que enviar la lista hacia la vista con model
        model.addAttribute("productoList", productoBusquedaList);

        //redirige a la vista
        return "admin/principal";
    }

    @GetMapping("logOut")
    public String logOut(HttpSession httpSession) {
        //Hacemos que la variable idAdmin sea null, de manera que ya no se tenga acceso a
        //apartados destinados para los administradores
        httpSession.removeAttribute("idAdmin");
        //Tras cerrar sesión, se devuelve a la página principal genérica
        return "redirect:/";
    }

    @GetMapping("detalleProducto/{idProducto}")
    public String detalleProducto(@PathVariable Integer idProducto, Model model, HttpSession httpSession) throws Exception {
        Producto producto = findProductoPort.findById(idProducto);
        model.addAttribute("producto", producto);
        if (httpSession.getAttribute("idAdmin") == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Ups, parece que te has perdido.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        if (httpSession.getAttribute("idAdmin") != null) {
            model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());
        }
        //nos retorna a la vista de detalle del producto de los admin
        return "admin/detalle_producto";
    }

    @GetMapping("visitarPerfil/{idUsuario}")
    public String visitarPerfil(@PathVariable Integer idUsuario, Model model, HttpSession httpSession) throws Exception {
        Usuario usuario = findUsuarioPort.findById(idUsuario);
        List<Producto> productosDelPerfil = findProductoPort.findAllByIdUsuario(usuario.getIdUsuario());
        model.addAttribute("usuario", usuario);
        model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());
        model.addAttribute("productosDelPerfil", productosDelPerfil);
        return "admin/perfil_usuario";
    }

    @PostMapping("avisar/{idUsuario}")
    public String avisar(@RequestParam Integer idUsuario, @RequestParam Integer idProducto, Model model, HttpSession httpSession) throws Exception {
        //primero comprobar que quien está accediendo es un admin
        if(httpSession.getAttribute("idAdmin")==null){
            ErrorPropio errorPropio = new ErrorPropio();
            errorPropio.setTexto("No tienes autorización para realizar esta petición.");
            model.addAttribute("error", errorPropio);
            return "usuario/modal_error";
        }
        String admin = httpSession.getAttribute("idAdmin").toString();
        Integer idAdminInt = Integer.parseInt(admin);
        Usuario adminActual = findUsuarioPort.findById(idAdminInt);

        String idProductoString = String.valueOf(idProducto);
        Usuario usuarioPropietario = findUsuarioPort.findById(idUsuario);

        model.addAttribute("idProducto", idProductoString);
        model.addAttribute("usuarioInteresado", adminActual);
        model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());
        model.addAttribute("usuarioPropietario", usuarioPropietario);

        //redirige al formulario de envío de avisos
        return "admin/enviar_aviso";
    }

    @PostMapping("enviarAviso")
    public String enviarAviso(Email email, Model model, HttpSession httpSession) throws Exception {
        //Necesitamos saber quién es el propietario

        Integer idPropietario = Integer.parseInt(email.getDestinatario());
        Usuario usuarioPropietario = findUsuarioPort.findById(idPropietario);

        //Necesitamos saber quién es el interesado

        String idUserInteresado = email.getEmisor();
        Integer idUserInteresadoInt = Integer.parseInt(idUserInteresado);
        Usuario usuarioActual = findUsuarioPort.findById(idUserInteresadoInt);
        String para = usuarioPropietario.getEmail();

        Integer idObjetoInt = Integer.parseInt(email.getIdObjeto());
        Producto producto = findProductoPort.findById(idObjetoInt);
        String url = "http://localhost:8080/detalleProducto/" + email.getIdObjeto();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("trashureappteam@gmail.com");
        simpleMailMessage.setTo(para);

        simpleMailMessage.setSubject("Un administrador te ha enviado un aviso.");
        simpleMailMessage.setText("Un administrador del equipo de Trashure te ha enviado un aviso sobre tu anuncio del " +
                "producto '" + producto.getNombre() +"':" + "\n '"
                + email.getTexto() + "'. \n" +
                "-- \n Te solicitamos que gestiones el aviso con la mayor brevedad posible." +
                " \n Puedes acceder al anuncio desde el siguiente enlace: " + url + " \n "
                + "¡Gracias por depositar tu confianza en Trashure!");
        System.out.println("mensaje : '" + simpleMailMessage.getText() + "'. \n" +
                "Puedes contactar con el usuario a través de su dirección de correo electrónico: " + usuarioActual.getEmail());


        model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            ErrorPropio err = new ErrorPropio();
            err.setTexto("Error al enviar un aviso al propietario.");
            model.addAttribute("error", err);
            return "usuario/modal_error";
            //System.out.println("Error al enviar email");
            //e.printStackTrace();
        }
        System.out.println("fin mensaje");

        return "redirect:/api/v0/admin/principal";
    }


    //Método para filtrar usuarios por id de usuario

    @GetMapping("filtrarUsers")
    public String filtrarUsuarios(Model model, @RequestParam String textoBusqueda, HttpSession httpSession) {

        List<Usuario> usuariosTotales = new ArrayList<>();

        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            usuariosTotales = findUsuarioPort.findAll();
        } else {
            usuariosTotales = findUsuarioPort.findAll().stream().filter
                    (x -> String.valueOf(x.getIdUsuario()).contentEquals((textoBusqueda).toString())).collect(Collectors.toList());
        }

        System.out.println("numero de usuarios en total " + usuariosTotales.size());

        model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());


        model.addAttribute("listaUsuarios", usuariosTotales);
        //redirige a la vista
        return "admin/usuarios";

    }

    //Método para filtrar productos por id de producto
    @GetMapping("filtrarProductos")
    public String filtrarProductos(Model model, @RequestParam String textoBusqueda, HttpSession httpSession) {

        List<Producto> productos = new ArrayList<>();

        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            productos = findProductoPort.findAll();
        } else {
            productos = findProductoPort.findAll().stream().filter
                    (x -> String.valueOf(x.getIdProducto()).contentEquals((textoBusqueda).toString())).collect(Collectors.toList());
        }

        System.out.println("numero de usuarios en total " + productos.size());


        model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());

        model.addAttribute("listaProductos", productos);
        //redirige a la vista
        return "producto/mostrar";

    }

    //Método para filtrar transacciones por id de transaccion, por id de donante, por id de solicitante o por id de producto
    @GetMapping("filtrarTransacciones")
    public String filtrarTransacciones(Model model, @RequestParam String textoBusqueda, @RequestParam String tipoId, HttpSession httpSession) {

        System.out.println("tipo id seleccionado " + tipoId);
        List<Transaccion> transaccionList = new ArrayList<>();


        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            transaccionList = findTransaccionPort.findAll();
        }
        if(textoBusqueda != null && !textoBusqueda.isEmpty()){
            switch (tipoId){
                case "Transaccion":
                    transaccionList = findTransaccionPort.findAll().stream().filter
                            (x -> String.valueOf(x.getIdTransaccion()).contentEquals((textoBusqueda).toString())).collect(Collectors.toList());
                    break;
                case "Solicitante":
                    transaccionList = findTransaccionPort.findAll().stream().filter
                            (x -> String.valueOf(x.getIdComprador()).contentEquals((textoBusqueda).toString())).collect(Collectors.toList());

                    break;
                case "Donante":
                    transaccionList = findTransaccionPort.findAll().stream().filter
                            (x -> String.valueOf(x.getIdVendedor()).contentEquals((textoBusqueda).toString())).collect(Collectors.toList());

                    break;
                case "Producto":
                    transaccionList = findTransaccionPort.findAll().stream().filter
                            (x -> String.valueOf(x.getIdProducto()).contentEquals((textoBusqueda).toString())).collect(Collectors.toList());

                    break;


            }

            }

        System.out.println("numero de usuarios en total " + transaccionList.size());

        model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());


        model.addAttribute("listaTransacciones", transaccionList);
        //redirige a la vista
        return "admin/transacciones";

    }
}
