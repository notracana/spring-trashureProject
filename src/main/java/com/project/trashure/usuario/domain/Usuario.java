package com.project.trashure.usuario.domain;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.transaccion.domain.Transaccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    //Definición de los atributos
    //Usando las notaciones de @Getter y @Setter de Lombok
    //se definen automáticamente los getters y setters de los
    //atributos

    //Igual ocurre con las notaciones @AllArgsConstructor y @NoArgsConstructor
    private String idUsuario;
    private String nombre;
    private String apellidos;
    private String username;

    private String password;
    private String email;

    private String direccion;
    private String telefono;


    //El campo userType sirve para distinguir entre los tipos de usuario
    private String tipoUsuario;

    List<Producto> listaProductos;

    List<Transaccion> listaCompras;

    List<Transaccion> listaVentas;

    public Usuario (UsuarioJpa usuarioJpa){
        if(usuarioJpa == null) return;

        this.setIdUsuario(usuarioJpa.getIdUsuario());
        this.setNombre(usuarioJpa.getNombre());
        this.setApellidos(usuarioJpa.getApellidos());
        this.setUsername(usuarioJpa.getUsername());
        this.setPassword(usuarioJpa.getPassword());
        this.setEmail(usuarioJpa.getEmail());
        this.setDireccion(usuarioJpa.getDireccion());
        this.setTelefono(usuarioJpa.getTelefono());
        this.setTipoUsuario(usuarioJpa.getTipoUsuario());
        this.setListaProductos(usuarioJpa.getListaProductos());
        this.setListaCompras(usuarioJpa.getListaCompras());
        this.setListaVentas(usuarioJpa.getListaVentas());
    }



}
