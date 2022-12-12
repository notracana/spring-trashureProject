package com.project.trashure.producto.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class CreateImagenProductoUseCase {

    //Se crea una variable de tipo String que contiene la ubicación donde se van a cargar las imágenes
    //en el proyecto
    //Las imágenes van a estar en el directorio imagenes dentro de la raiz del proyecto
    private String carpetaImagenes = "imagenes//";

    //El primer método sirve para subir imágenes y como parámetro recibe un objeto de tipo MultipartFile

    public String saveImagen(MultipartFile multipartFile) throws IOException {
        //Si se carga la imagen del producto...
        if (!multipartFile.isEmpty()){
            //... entonces pasamos el archivo de la imagen a bytes para poderla pasar del cliente al servidor

            byte[] bytesImagen = multipartFile.getBytes();

            //determinamos la ubicación donde se va a aguardar la imagen (la carpeta definida antes)

            Path pathImagen = Paths.get(carpetaImagenes + multipartFile.getOriginalFilename());

            //Se escribe en la ruta estipulada. Para eso le pasamos la ruta Path y la imagen en bytes por parámetros
            Files.write(pathImagen, bytesImagen);

            //se retorna el nombre de la imagen subida para guardarla en el campo correspondiente de producto
            return multipartFile.getOriginalFilename();

        }
        //Si, en cambio, al subir un producto, no se sube una imagen del mismo...
        //en ese caso usamos una imagen por defecto y se va a guardar

        return "defaultImagenProducto.jpg";

    }
    //MIRAR ESTO:

    //DEBERÍA ESTAR EN DELETEIMAGENPRODUCTO??
    //Método para borrar una imagen de producto
    //Recibe como parámetro el nombre de la imagen
    //Si, pore ejemplo, se borra el producto, también se tiene que borrar su imagen
    //y si quiero borrar la foto para subir otra?
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
