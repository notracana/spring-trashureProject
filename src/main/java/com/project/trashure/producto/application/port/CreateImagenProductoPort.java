package com.project.trashure.producto.application.port;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CreateImagenProductoPort {

    public String saveImagen(MultipartFile multipartFile) throws IOException;


}
