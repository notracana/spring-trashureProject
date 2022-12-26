package com.project.trashure.perfil.infrastructure.controller;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private FindProductoPort findProductoPort;
    @GetMapping("/principal")
    public String principal(Model model){
        //el método principal recibe como parámetro un objeto Model
        //para enviar los productos a la vista principal

        //se crea una lista de los productos y se recuperan todos los guardados
        List<Producto> productoList = findProductoPort.findAll();
        //a model, addAttribute con parámetros elñ nombre de la varaible con lo que lo voy a neviar
        //y el contenido de la lista
        //de esta forma, al llamar a este método, se obtendrían los productos
        //y pasan a la vista principal
        model.addAttribute("productos", productoList);

        //es necesario que en la vista principal.html para iterar sobre los cards a lo largo de la lista de productos
    return "admin/principal";
    }
}
