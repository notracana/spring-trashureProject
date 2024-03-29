package com.project.trashure.producto.domain;

import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private Integer idProducto;

    private String nombre;

    private String descripcion;

    private Integer idUsuario;
    private String estado;

    private String categoria;

    private String imagen;

    //El campo price realmente hace referencia al tipo de venta al que está sujeto
    //porque el usuario puede elegir la modalidad de intercambiar o simplemente regalar el producto
    //private String precio;

    //private Integer cantidad;

    //private String idUsuario;

    private String disponibilidad;

    private Usuario propietario;

    //List<Usuario> favoritosDe;

    List<Usuario> usuarios;

    private ProductoJpa productoJpa;

    public Producto (ProductoJpa productoJpa){
        if(productoJpa == null) return;

        this.productoJpa = productoJpa;
        this.setIdProducto(productoJpa.getIdProducto());
        this.setNombre(productoJpa.getNombre());
        this.setDescripcion(productoJpa.getDescripcion());
        this.setEstado(productoJpa.getEstado());
        this.setCategoria(productoJpa.getCategoria());
        this.setImagen(productoJpa.getImagen());
        //this.setPropietario(productoJpa.getPropietarioJpa());
        //this.setFavoritosDe(productoJpa.getFavoritosDe());
        //this.setPrecio(productoJpa.getPrecio());
        //this.setCantidad(productoJpa.getCantidad());
        //this.setIdUsuario(productoJpa.getIdUsuario());
        this.setDisponibilidad(productoJpa.getDisponibilidad());
        this.setIdUsuario(productoJpa.getIdUsuario());
    }


    public Usuario getPropietario(){
        if(this.propietario != null) return propietario;
        if(this.productoJpa == null) return null;
        UsuarioJpa propietarioJpa = this.productoJpa.getPropietarioJpa();

        if(propietarioJpa == null) return null;
        Usuario propietario = new Usuario(propietarioJpa);
        this.setPropietario(propietario);
        return propietario;

    }

    public List<Usuario> getUsuarios(){
        if(this.usuarios != null) return this.usuarios;
        if(this.getProductoJpa() == null) return null;
        List<UsuarioJpa> usuariosJpa = getProductoJpa().getUsuarios();
        if(usuariosJpa == null) return null;
        List<Usuario> usuarios = usuariosJpa.stream().map(Usuario::new).collect(Collectors.toList());
        this.setUsuarios(usuarios);
        return usuarios;
    }

}
