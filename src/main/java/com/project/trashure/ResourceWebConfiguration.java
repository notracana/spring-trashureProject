package com.project.trashure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //como parámetro de addResourceHandler ponemos la cadena con la carpeta de imagenes y dos **
        //que significan que se incluye aquello que haya dentro
        //addResourceLocations indica la ubicación a la que se apunta (al directorio imagenes
        registry.addResourceHandler("/imagenes/**").addResourceLocations("file:imagenes/");
    }
}

