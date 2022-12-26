package com.project.trashure.producto.infrastructure.controller;

import com.project.trashure.producto.application.CreateImagenProductoUseCase;
import com.project.trashure.producto.application.port.CreateImagenProductoPort;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/v0/imagenProductos")
@AllArgsConstructor
public class CreateImagenProductoController {

private CreateImagenProductoPort createImagenProductoPort;

}
