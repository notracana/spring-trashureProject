package com.project.trashure.usuario.infrastructure.controller;

import com.project.trashure.error.ErrorPropio;
import com.project.trashure.exito.Exito;
import com.project.trashure.usuario.infrastructure.repository.port.DeleteUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v0/usuarios")
@AllArgsConstructor
public class DeleteUsuarioController {

    private DeleteUsuarioPort deleteUsuarioPort;


    @GetMapping("del/{idUsuario}")
    @Transactional(rollbackFor = Exception.class)
    public String delete(@PathVariable("idUsuario") Integer idUsuario, HttpSession httpSession, Model model) {
        //COMPROBAR QUE EL USUARIO ES ADMINISTRADOR PARA HACER ESTA ACCION
        if (httpSession.getAttribute("idAdmin") == null) {
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Petición no autorizada.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        deleteUsuarioPort.deleteById(idUsuario);
        return "redirect:/api/v0/admin/getUsuarios";
    }

    @GetMapping("delAcc/{idUsuario}")
    @Transactional(rollbackFor = Exception.class)
    public String deleteAccount(@PathVariable("idUsuario") Integer idUsuario, HttpSession httpSession, Model model) {
        //COMPROBAR QUE EL USUARIO ES EL PROPIO USUARIO PARA HACER ESTA ACCION
        System.out.println("id parametro " + idUsuario.toString());
        System.out.println("id usuario sesion " +  httpSession.getAttribute("idUsuario").toString());
        if (httpSession.getAttribute("idUsuario") == null || !httpSession.getAttribute("idUsuario").toString().contentEquals(idUsuario.toString())) {
            System.out.println("different " + httpSession.getAttribute("idUsuario").toString() != idUsuario.toString());
            System.out.println(" diff " + !httpSession.getAttribute("idUsuario").toString().contentEquals(idUsuario.toString()));
            ErrorPropio ep = new ErrorPropio();
            ep.setTexto("Petición no autorizada.");
            model.addAttribute("error", ep);
            return "usuario/modal_error";
        }
        //Eliminamos el atributo idUsuario
        httpSession.removeAttribute("idUsuario");

        deleteUsuarioPort.deleteById(idUsuario);
        //Tras eliminar la cuenta, se muestra un modal de éxito
        Exito exito = new Exito();
        exito.setTexto("Ha eliminado correctamente su cuenta. Esperamos verte de nuevo.");
        model.addAttribute("success", exito);
        return "usuario/modal_exito";
    }
}
