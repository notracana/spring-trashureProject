package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.usuario.application.port.CreateUsuarioPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v0/usuarios")
@AllArgsConstructor
public class CreateUsuarioController {

    private CreateUsuarioPort createUsuarioPort;

    private FindUsuarioPort findUsuarioPort;

    @GetMapping("/signUp")
    public String signUp(){
        //redirige a la vista de registro del usuario en la app
        return "usuario/sign_up";
    }

    @PostMapping("/registrarse")
    public String registrarse(Usuario usuario){

        //El usuario que llega como parámetro viene de los datos del formulario de la vista sign_up
        //hay que determinar que el usuario que se registra es de tipo normal, es decir, no es administrador

        usuario.setTipoUsuario("USER");

        //se crea el usuario con los datos aportados
        createUsuarioPort.create(usuario);
        //Tras guardar el nuevo usuario en la base de datos, redirige a la página principal de usuario
        return "redirect:/";
    }

    //MIRAR ESTO: ME LLEVARÍA ESTE MÉTODO A OTRA PARTE VID 37
    //método para acceder a la vista de iniciar sesión
    @GetMapping("/logIn")
    public String logIn(){
        //redirige a la vista para iniciar sesion
        return "usuario/log_in";
    }


    @PostMapping("/iniciarSesion")
    public String iniciarSesion(Usuario usuario, HttpSession httpSession) throws Exception {

        Usuario usuario1 = new Usuario();
        //Si el usuario existe en la base de datos...

         try{
             usuario1 = findUsuarioPort.findByUsername(usuario.getUsername());
         }
         catch (Exception e) {
             e.getStackTrace();
             throw new Exception("No existe ningún usuario con ese username. ");
         }

         String password = usuario1.getPassword();

         if(!password.contentEquals(usuario.getPassword().toString())){
             System.out.println("password del usuario " + password);
             System.out.println("password introducida " + usuario.getPassword());

             throw new Exception("La contraseña introducida no es correcta. ");
         }

        //MIRAR ESTO: SE DEBERÍA CORROBORAR QUE LA CONTRASEÑA COINCIDA

        String tipo = usuario1.getTipoUsuario();
        //En el objeto de tipo HttpSession se guarda el idUsuario
        int id = usuario1.getIdUsuario();
        httpSession.setAttribute("idUsuario", String.valueOf(id));
        //Una vez obtenido el id y guardado en HttpSession, se redirige a distintas vistas
        //según el tipo de usuario
        if (tipo.equals("ADMINISTRADOR")){

            //Si es administrador, se le redirige a la vista del admin
            return "redirect:/principal";
        }
        if(tipo.equals("USER")){
            //Si es de tipo usuario genérico, se le redirige a la página principal
            return "redirect:/";
        }

        //Al final redirige hacia la página principal
        return "redirect:/";
    }

    @GetMapping("logOut")
    public String logOut(HttpSession httpSession){
        //Hacemos que la variable idUsuario sea null, de manera que ya no se tenga acceso a
        //apartados destinados para los usuarios logueados
        httpSession.removeAttribute("idUsuario");
        //Tras cerrar sesión, se devuelve a la página principal
        return "redirect:/";
    }

    @PutMapping("/update")
    public void updateUsuario(){

    }

}
