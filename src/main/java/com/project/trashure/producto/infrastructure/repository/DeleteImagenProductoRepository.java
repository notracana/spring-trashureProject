package com.project.trashure.producto.infrastructure.repository;

import com.project.trashure.producto.infrastructure.repository.port.DeleteImagenProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.File;

@Repository
@AllArgsConstructor
public class DeleteImagenProductoRepository implements DeleteImagenProductoPort {

    //MIRAR ESTO:

    //DEBERÍA ESTAR EN DELETEIMAGENPRODUCTO??
    //Método para borrar una imagen de producto
    //Recibe como parámetro el nombre de la imagen
    //Si, pore ejemplo, se borra el producto, también se tiene que borrar su imagen
    //y si quiero borrar la foto para subir otra?
    @Override
    public void deleteImagenProducto (String nombreImagen){
        //Variable String con la ruta donde está guardada la imagen

        String carpetaImagenes = "imagenes//";
        String rutaCompleta = carpetaImagenes + nombreImagen;
        //Variable de tipo File al que le pasamos como parámetro la ruta de la imagen y el nombre de la imagen
        File fileImagen = new File(rutaCompleta);
        //se borra la imagen
        fileImagen.delete();

    }
}
