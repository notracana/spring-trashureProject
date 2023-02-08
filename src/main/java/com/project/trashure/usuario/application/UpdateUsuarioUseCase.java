package com.project.trashure.usuario.application;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.infrastructure.repository.port.FindProductoPort;
import com.project.trashure.producto.infrastructure.repository.port.SaveProductoPort;
import com.project.trashure.usuario.application.port.UpdateUsuarioPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import com.project.trashure.usuario.infrastructure.repository.port.SaveUsuarioPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUsuarioUseCase implements UpdateUsuarioPort {
    private FindUsuarioPort findUsuarioPort;
    private SaveUsuarioPort saveUsuarioPort;


    @Override
    public Usuario update(Integer idUsuario, String nombre, String apellidos, String email, String telefono, String direccion, String localidad) throws Exception {
        Usuario usuario = findUsuarioPort.findById(idUsuario);
        if(nombre!=null) usuario.setNombre(nombre);
        if(apellidos!=null) usuario.setApellidos(apellidos);
        if(email!= null)usuario.setEmail(email);
        if(telefono!=null) usuario.setTelefono(telefono);
        if(direccion!=null)usuario.setDireccion(direccion);
        if(localidad!=null) usuario.setLocalidad(localidad);
        return saveUsuarioPort.save(usuario);
    }

    @Override
    public Usuario update(Integer idUsuario, String password) throws Exception {
        Usuario usuario = findUsuarioPort.findById(idUsuario);
        if(password!=null) usuario.setPassword(password);
        return saveUsuarioPort.save(usuario);
    }
}
