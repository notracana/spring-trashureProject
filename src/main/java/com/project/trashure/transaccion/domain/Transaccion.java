package com.project.trashure.transaccion.domain;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Transaccion {
    private Integer idTransaccion;
    private String idVendedor;
    private String idComprador;

    //El estado puede ser "pendiente", "aceptada", "rechazada"
    private String estado;
    //private String idProducto;
    private Date fechaTransaccion;

    private Usuario usuario;

    private TransaccionJpa transaccionJpa;

    private Producto producto;

    public Transaccion (TransaccionJpa transaccionJpa){
        if(transaccionJpa == null){return;}
        this.setIdTransaccion(transaccionJpa.getIdTransaccion());
        this.setIdVendedor(transaccionJpa.getIdVendedor());
        this.setIdComprador(transaccionJpa.getIdComprador());
        this.setEstado(transaccionJpa.getEstado());
        //this.setIdProducto(transaccionJpa.getIdProducto());
        this.setFechaTransaccion(transaccionJpa.getFechaTransaccion());
    }

    public Usuario getUsuario() {
        if(usuario != null) return usuario;
        if(transaccionJpa == null) return null;
        UsuarioJpa usuarioJpa = transaccionJpa.getUsuarioJpa();

        if(usuarioJpa == null) return null;
        Usuario usuario1 = new Usuario(usuarioJpa);
        this.setUsuario(usuario1);
        return usuario1;

    }

}
