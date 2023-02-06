package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.usuario.application.port.UpdateUsuarioPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("api/v0/usuarios")
public class UpdateUsuarioController {
    private FindUsuarioPort findUsuarioPort;

    private UpdateUsuarioPort updateUsuarioPort;

    @PostMapping("/update")
    public String updateUsuario(Usuario usuario) throws Exception {
        Usuario usuario1 = findUsuarioPort.findById(usuario.getIdUsuario());

        usuario.setIdUsuario(usuario1.getIdUsuario());

        //MIRAR ESTO
        //EL UPDATE NOP ME GFUSTA. COMPARAR CON EL SUYO EN MIN 13.50 VIDEO 18
        updateUsuarioPort.update(usuario1.getIdUsuario(), usuario1.getNombre(), usuario1.getApellidos(), usuario1.getEmail(), usuario1.getTelefono(),
                usuario1.getDireccion(), usuario1.getLocalidad());
        return "redirect:/usuario/perfil_propio";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(Usuario usuario) throws Exception {
        Usuario usuario1 = findUsuarioPort.findById(usuario.getIdUsuario());

        if(usuario.getPassword().equals(usuario1.getPassword())){
           // usuario1.setPassword(usuario.get);
        }
        usuario.setIdUsuario(usuario1.getIdUsuario());

        //MIRAR ESTO
        //EL UPDATE NOP ME GFUSTA. COMPARAR CON EL SUYO EN MIN 13.50 VIDEO 18
        updateUsuarioPort.update(usuario1.getIdUsuario(), usuario1.getNombre(), usuario1.getApellidos(), usuario1.getEmail(), usuario1.getTelefono(),
                usuario1.getDireccion(), usuario1.getLocalidad());
        return "redirect:/usuario/perfil_propio";
    }
}
