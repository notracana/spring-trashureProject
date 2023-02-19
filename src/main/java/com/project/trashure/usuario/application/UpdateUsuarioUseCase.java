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

import java.util.List;

@Service
@AllArgsConstructor
public class UpdateUsuarioUseCase implements UpdateUsuarioPort {
    private FindUsuarioPort findUsuarioPort;
    private SaveUsuarioPort saveUsuarioPort;

    private FindProductoPort findProductoPort;

    private SaveProductoPort saveProductoPort;


    @Override
    public void updateInfo(Integer idUsuario, String nombre, String apellidos, String email, String telefono, String direccion, String localidad) throws Exception {
        Usuario usuario = findUsuarioPort.findById(idUsuario);
        List<Producto> productosSubidos = usuario.getProductosSubidos();
        List<Producto> productoList = findProductoPort.findAllByPropietario(usuario);

        if (nombre != null) usuario.setNombre(nombre);
        if (apellidos != null) usuario.setApellidos(apellidos);
        if (email != null) usuario.setEmail(email);
        if (telefono != null) usuario.setTelefono(telefono);
        if (direccion != null) usuario.setDireccion(direccion);
        if (localidad != null) usuario.setLocalidad(localidad);
        //usuario.setProductosSubidos(productosSubidos);


        Usuario usuarioSaved = saveUsuarioPort.save(usuario);
        System.out.println("id usuario que viene por parametro " + idUsuario);
        System.out.println("id usuario una vez se ha actualizado " + usuarioSaved.getIdUsuario());
        System.out.println("tamaño de la lista de productos " + productoList.size());
        System.out.println("productos del señor este "+ usuarioSaved.getProductosSubidos().size());

        /*
        for (Producto p : productoList) {
            p.setIdUsuario(usuarioSaved.getIdUsuario());
            System.out.println("id usuario de lños productos de la lista en el for " + p.getIdUsuario());
            p.setPropietario(usuarioSaved);
            System.out.println("id usuario de lños productos de la lista en el for " + p.getIdUsuario());
            Producto savedProduct = saveProductoPort.save(p);
            System.out.println("save product " + savedProduct.getNombre() + ", " + savedProduct.getIdUsuario() );
        }*/
        for (Producto p : productoList) {

            System.out.println(" product " + p.getNombre() + ", " + p.getIdUsuario() );
        }
        //return usuarioSaved;
    }

    @Override
    public Usuario updatePass(Integer idUsuario, String password) throws Exception {
        Usuario usuario = findUsuarioPort.findById(idUsuario);
        //List<Producto> productosSubidos = usuario.getProductosSubidos();
        List<Producto> productoList = findProductoPort.findAllByPropietario(usuario);
        if (password != null) usuario.setPassword(password);
        //usuario.setProductosSubidos(productosSubidos);
        Usuario usuarioSaved = saveUsuarioPort.save(usuario);

        System.out.println("id usuario que viene por parametro " + idUsuario);
        System.out.println("id usuario una vez se ha actualizado " + usuarioSaved.getIdUsuario());
        System.out.println("tamaño de la lista de productos " + productoList.size());
        for (Producto p : productoList) {
            p.setIdUsuario(usuarioSaved.getIdUsuario());
            p.setPropietario(usuarioSaved);
            System.out.println("id usuario de lños productos de la lista en el for " + p.getIdUsuario());
            Producto savedProduct = saveProductoPort.save(p);
            System.out.println("save product " + savedProduct.getNombre() + ", " + savedProduct.getIdUsuario() + ", " + savedProduct.getPropietario().getIdUsuario());
        }

        return usuarioSaved;
    }
}
