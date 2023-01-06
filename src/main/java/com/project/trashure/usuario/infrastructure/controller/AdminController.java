package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.infrastructure.repository.port.FindTransaccionPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.TreeSet;

@Controller
@AllArgsConstructor
@RequestMapping("api/v0/admin")
public class AdminController {

    private FindProductoPort findProductoPort;

    private FindUsuarioPort findUsuarioPort;

    private FindTransaccionPort findTransaccionPort;
    @GetMapping("/principal")
    public String principal(Model model){
        //el método principal recibe como parámetro un objeto Model
        //para enviar los productos a la vista principal

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
    public String getUsuarios (Model model){

        List<Usuario> usuarioList = findUsuarioPort.findAll();
        //Se envía la lista de usuarios a la vista
        model.addAttribute("listaUsuarios", usuarioList);

        //Redirige a la vista de los usuarios de Admin
        return "admin/usuarios";
    }


    //Método para llevar a la vista de admin de Transacciones
    @GetMapping("/getTransacciones")
    public String getTransacciones (Model model){

        List<Transaccion> transaccionList = findTransaccionPort.findAll();
        //Se envía la lista de transacciones a la vista
        model.addAttribute("listaTransacciones", transaccionList);

        //Redirige a la vista de las transacciones de Admin
        return "admin/transacciones";
    }

}
