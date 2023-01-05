package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.FindProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/v0/productos")
@AllArgsConstructor
public class GetProductoController {

    private FindProductoRepository findProductoRepository;

    //Queremos redireccionar hacia la vista mostrar.html, dentro del directorio producto, en templates
    //Le pasamos como parámetro un objeto de la clase Model, que traslada información desde
    //el back a la vista, en este caso, pasa la lista de productos
    @GetMapping("/getProductos")
    public String mostrar(Model model){
        //el método addAtrribute recibe dos parámetros, el primero hace referencia al nombre
        //con el que se recibe la info (listaProductos) y el segundo es la variable con el valor
        List<Producto> listaProductos = findProductoRepository.findAll();
        if(listaProductos.isEmpty()){
            model.addAttribute("listaProductos", new ArrayList<Producto>());
        }
        else{        model.addAttribute("listaProductos", findProductoRepository.findAll());
        }
        //La lista de productos va a ser enviada a la vista producto/mostrar
        return "producto/mostrar";
    }

    @GetMapping("/crearProductos")
    public String crear(){
        return "producto/crear";
    }
}
