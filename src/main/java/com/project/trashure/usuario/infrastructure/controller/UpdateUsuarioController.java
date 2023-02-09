package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.usuario.application.port.UpdateUsuarioPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("api/v0/usuarios")
public class UpdateUsuarioController {
    private FindUsuarioPort findUsuarioPort;

    private UpdateUsuarioPort updateUsuarioPort;

    @PostMapping("/update")
    public String updateUsuario(Usuario usuario, Model model, HttpSession httpSession) throws Exception {
        System.out.println("heyy qué pasa makinasa");
        //Usuario usuario1 = findUsuarioPort.findById(usuario.getIdUsuario());

        System.out.println("usuario id parametro " + usuario.getIdUsuario());

        String id =  httpSession.getAttribute("idUsuario").toString();
        Integer idInt = Integer.parseInt(id);
        Usuario usuarioHttp = findUsuarioPort.findById(idInt);

        System.out.println("usuario http id " + usuarioHttp.getIdUsuario());
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
        //usuario.setIdUsuario(usuario1.getIdUsuario());

        //MIRAR ESTO
        //EL UPDATE NOP ME GFUSTA. COMPARAR CON EL SUYO EN MIN 13.50 VIDEO 18
        updateUsuarioPort.update(usuarioHttp.getIdUsuario(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getTelefono(),
                usuario.getDireccion(), usuario.getLocalidad());

        return "redirect:/api/v0/usuarios/miPerfil";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(Usuario usuario, Model model, HttpSession httpSession) throws Exception {

        String id =  httpSession.getAttribute("idUsuario").toString();
        Integer idInt = Integer.parseInt(id);
        Usuario usuarioHttp = findUsuarioPort.findById(idInt);

        System.out.println("usuario http id " + usuarioHttp.getIdUsuario());
        model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());

        if(!usuario.getNombre().equals(usuarioHttp.getPassword())){
            //contraseña actual correcta
            throw new Exception("La contraseña actual no es correcta");
        }
        String pass1 = usuario.getApellidos().toString();
        updateUsuarioPort.update(usuarioHttp.getIdUsuario(), pass1);
        return "redirect:/api/v0/usuarios/miPerfil";

    }

    @GetMapping("/modificarPerfil")
    public String modificarPerfil(Model model, HttpSession httpSession) throws Exception {
        Usuario usuario = new Usuario();
        if (httpSession.getAttribute("idUsuario") != null) {
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
            String idUsuario = httpSession.getAttribute("idUsuario").toString();
            Integer id = Integer.parseInt(idUsuario);
            usuario = findUsuarioPort.findById(id);
            model.addAttribute("usuario", usuario);

        } else {
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario"));
        }


        return "usuario/editar_perfil";
    }
    @GetMapping("/miPerfil")
    public String getMiPerfil(Model model, HttpSession httpSession) throws Exception {
        System.out.println("hola");
        Usuario usuario = new Usuario();
        if (httpSession.getAttribute("idUsuario") != null) {
            System.out.println("hola tengo usuario");
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
            String idUsuario = httpSession.getAttribute("idUsuario").toString();
            Integer id = Integer.parseInt(idUsuario);
            usuario = findUsuarioPort.findById(id);
            model.addAttribute("usuario", usuario);

            System.out.println("id usuario " + usuario.getIdUsuario());
        } else {
            System.out.println("hola no tengo usuario");
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario"));
        }

        /*List<Producto> listaProductos = new ArrayList<>();
        listaProductos = findProductoPort.findAllByPropietario(usuario);
        model.addAttribute("listaProductos", listaProductos);
        //Hace return hacia la vista de favoritos dentro de usuario

*/
        return "usuario/perfil_propio";
    }

    @GetMapping("/changePassword")
    public String modificarPassword (Model model, HttpSession httpSession) throws Exception {

        Usuario usuario = new Usuario();
        if (httpSession.getAttribute("idUsuario") != null) {
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());
            String idUsuario = httpSession.getAttribute("idUsuario").toString();
            Integer id = Integer.parseInt(idUsuario);
            usuario = findUsuarioPort.findById(id);
            model.addAttribute("usuario", usuario);

        } else {
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario"));
        }


        return "usuario/editar_password";
    }

}
