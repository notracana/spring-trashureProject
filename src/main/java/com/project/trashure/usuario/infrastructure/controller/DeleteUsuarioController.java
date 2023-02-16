package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.error.ErrorPropio;
import com.project.trashure.usuario.infrastructure.repository.port.DeleteUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v0/usuarios")
@AllArgsConstructor
public class DeleteUsuarioController {

    private DeleteUsuarioPort deleteUsuarioPort;


    @DeleteMapping("{idUsuario}")
    @Transactional(rollbackFor = Exception.class)
    public String delete(@PathVariable("idUsuario") Integer idUsuario, HttpSession httpSession, Model model){
        //COMPROBAR QUE EL USUARIO ES ADMINISTRADOR PARA HACER ESTA ACCION
        if(httpSession.getAttribute("idAdmin") == null){
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("BAD GATEWAY. NO ACCESS");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        deleteUsuarioPort.deleteById(idUsuario);
        return "redirect:/api/v0/admin/getUsuarios";
    }
}
