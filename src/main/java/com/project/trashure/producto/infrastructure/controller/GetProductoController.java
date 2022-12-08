package com.project.trashure.producto.infrastructure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v0/productos")
public class GetProductoController {

    //Queremos redireccionar hacia la vista show.html, dentro del directorio producto, en templates
    @GetMapping("/getProductos")
    public String show(){
        return "producto/show";
    }
}
