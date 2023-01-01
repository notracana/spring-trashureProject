package com.project.trashure.transaccion.infrastructure.controller;

import com.project.trashure.detalletransaccion.domain.DetalleTransaccion;
import com.project.trashure.detalletransaccion.infrastructure.repository.SaveDetalleTransaccionRepository;
import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.infrastructure.repository.port.SaveTransaccionPort;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("api/v0/transacciones")
public class SaveTransaccionController {

    private SaveTransaccionPort saveTransaccionPort;

    private SaveDetalleTransaccionRepository saveDetalleTransaccionRepository;

    //A este método se llama cuando se da a finalizar compra de un producto
    @GetMapping("/salvarTransaccion")
    public String salvarTransaccion(){

        Date fechaTransaccion = new Date();

        //Se tiene que crear un objeto de tipo Transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setFechaTransaccion(fechaTransaccion);
        transaccion.setIdComprador("idComprador"); //esto se tiene que sacar la de información de la sesión
        //transaccion.setIdVendedor(); ESTO LO PODRÍAMOS PONER EN LOS DETALLES
        //transaccion.setIdTransaccion(saveTransaccionPort.generarNumeroOrden(0));
        Transaccion transaccionSaved = saveTransaccionPort.save(transaccion);

        //Se ha de generar un objeto de tipo DetalleTransaccion

        DetalleTransaccion detalleTransaccion = new DetalleTransaccion();
        detalleTransaccion.setIdTransaccion(transaccionSaved.getIdTransaccion());
        //MIRAR ESTO HACE FALTA HACER ESTO..LO COMENTO PARA EL COMMIT
         detalleTransaccion.setIdProducto(___);
        saveDetalleTransaccionRepository.save(detalleTransaccion);

        //tras finalizar la compra, se debe devolver a la pantalla principal
        return "redirect:/";
    }
}
