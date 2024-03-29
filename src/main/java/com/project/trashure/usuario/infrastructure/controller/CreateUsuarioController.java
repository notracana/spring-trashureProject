package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.error.ErrorPropio;
import com.project.trashure.exito.Exito;
import com.project.trashure.usuario.application.port.CreateUsuarioPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import jakarta.mail.Message;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v0/usuarios")
@AllArgsConstructor
public class CreateUsuarioController {

    private CreateUsuarioPort createUsuarioPort;

    private FindUsuarioPort findUsuarioPort;

    @GetMapping("/signUp")
    public String signUp() {
        //redirige a la vista de registro del usuario en la app
        return "usuario/sign_up";
    }

    @PostMapping("/registrarse")
    public String registrarse(Usuario usuario, Model model) throws NoSuchAlgorithmException {

        //El usuario que llega como parámetro viene de los datos del formulario de la vista sign_up
        //hay que determinar que el usuario que se registra es de tipo normal, es decir, no es administrador

        usuario.setTipoUsuario("USER");

        //se crea el usuario con los datos aportados
        try{
        createUsuarioPort.create(usuario);}
        catch(Exception e){
            ErrorPropio errorPropio = new ErrorPropio();
            errorPropio.setTexto(e.getMessage());
            model.addAttribute("error", errorPropio);
            return "usuario/modal_error";
        }

        Exito exito = new Exito();
        exito.setTexto("Te has registrado exitosamente.");
        model.addAttribute("success", exito);
        return "usuario/modal_exito";

        //Tras guardar el nuevo usuario en la base de datos, redirige a la página principal de usuario
        //return "redirect:/";

    }

    //MIRAR ESTO: ME LLEVARÍA ESTE MÉTODO A OTRA PARTE VID 37
    //método para acceder a la vista de iniciar sesión
    @GetMapping("/logIn")
    public String logIn() {
        //redirige a la vista para iniciar sesion
        return "usuario/log_in";
    }


    @PostMapping("/iniciarSesion")
    public String iniciarSesion(Usuario usuario, Model model, HttpSession httpSession) throws Exception {

        Usuario usuario1 = new Usuario();
        //Si el usuario existe en la base de datos...

        try {
            usuario1 = findUsuarioPort.findByUsername(usuario.getUsername());
        } catch (Exception e) {

            ErrorPropio err = new ErrorPropio();
            err.setTexto("No existe ningún usuario con ese username. ");
            model.addAttribute("error", err);
            return "usuario/modal_error";

            //e.getStackTrace();
            //throw new Exception("No existe ningún usuario con ese username. ");
        }

        //SI ESTO LO COGEMOS DE LA BASE DE DATOS, VIENE HASHED


        String passwordInput = usuario.getPassword();

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] array = messageDigest.digest(passwordInput.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, array);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        //Se corrobora que la contraseña introducida en el login coincida con la contraseña almacenada en la bdd para ese usuario
        // lo que se compara es el hash de las contraseñas

        if (!usuario1.getPassword().contentEquals(hexString.toString())) {

            ErrorPropio err = new ErrorPropio();
            err.setTexto("La contraseña introducida no es correcta.  ");
            model.addAttribute("error", err);
            return "usuario/modal_error";
        }


        String tipo = usuario1.getTipoUsuario();
        //En el objeto de tipo HttpSession se guarda el idUsuario
        int id = usuario1.getIdUsuario();

        //Una vez obtenido el id y guardado en HttpSession, se redirige a distintas vistas
        //según el tipo de usuario
        if (tipo.equals("ADMINISTRADOR")) {

            httpSession.setAttribute("idAdmin", String.valueOf(id));
            model.addAttribute("adminLogged", httpSession.getAttribute("idAdmin").toString());

            //Si es administrador, se le redirige a la vista del admin

            return "redirect:/api/v0/admin/principal";
        }


        if (tipo.equals("USER")) {
            //Si es de tipo usuario genérico, se le redirige a la página principal
            httpSession.setAttribute("idUsuario", String.valueOf(id));
            model.addAttribute("usuarioLogged", httpSession.getAttribute("idUsuario").toString());

            return "redirect:/";
        }

        //Al final redirige hacia la página principal
        return "redirect:/";
    }

    @GetMapping("logOut")
    public String logOut(HttpSession httpSession, Model model) {
        //Hacemos que la variable idUsuario sea null, de manera que ya no se tenga acceso a
        //apartados destinados para los usuarios logueados
        httpSession.removeAttribute("idUsuario");
        //Tras cerrar sesión, se devuelve a la página principal

        Exito e = new Exito();
        e.setTexto("Has cerrado la sesión correctamente. Esperamos volver a verte pronto.");
        model.addAttribute("success", e);
        return "usuario/modal_exito";
        //return "redirect:/";
    }

    @PutMapping("/update")
    public void updateUsuario() {

    }

}
