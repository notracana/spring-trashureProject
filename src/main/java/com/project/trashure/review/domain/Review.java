package com.project.trashure.review.domain;

import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private Integer idReview;

    private Integer idTransaccion;
    private Integer idUsuarioAutor;

    private Integer idUsuarioDestinatario;


    //private String titulo;
    private String opinion;
    private Date fechaCreacion;
    //private Double nota;

    private Transaccion transaccion;


    private ReviewJpa reviewJpa;


    //private Usuario usuario;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", updatable = false, insertable = false)
    private UsuarioJpa usuarioJpa;*/


    public Review (ReviewJpa reviewJpa){
        if(reviewJpa == null) return;
        this.setIdReview(reviewJpa.getIdReview());
        this.setIdUsuarioAutor(reviewJpa.getIdUsuarioAutor());
        this.setIdUsuarioDestinatario(reviewJpa.getIdUsuarioDestinatario());
        this.setIdTransaccion(reviewJpa.getIdTransaccion());
        this.setOpinion(reviewJpa.getOpinion());
        this.setFechaCreacion(reviewJpa.getFechaCreacion());
        //this.setNota(reviewJpa.getNota());
    }
    public Transaccion getTransaccion() {
        if(transaccion != null) return transaccion;
        if(reviewJpa == null) return null;
        TransaccionJpa transaccionJpa1 = reviewJpa.getTransaccionJpa();

        if(transaccionJpa1 == null) return null;
        Transaccion transaccion1 = new Transaccion(transaccionJpa1);
        this.setTransaccion(transaccion1);
        return transaccion1;

    }
    /*public Usuario getUsuario() {
        if(usuario != null) return usuario;
        if(reviewJpa == null) return null;
        UsuarioJpa usuarioJpa = reviewJpa.getUsuarioJpa();

        if(usuarioJpa == null) return null;
        Usuario usuario1 = new Usuario(usuarioJpa);
        this.setUsuario(usuario1);
        return usuario1;

    }*/


}
