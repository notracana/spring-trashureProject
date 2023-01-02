package com.project.trashure.review.domain;

import com.project.trashure.transaccion.domain.TransaccionJpa;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import jakarta.persistence.Entity;
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

    private String idReview;
    private String autor;
    private String titulo;
    private String opinion;
    private Date fechaCreacion;
    private Double nota;

    private ReviewJpa reviewJpa;

    private Usuario usuario;
/*
    public Review (ReviewJpa reviewJpa){
        if(reviewJpa == null){return;}
        this.setIdReview(reviewJpa.getIdReview());
        this.setAutor(reviewJpa.getAutor());
        this.setTitulo(reviewJpa.getTitulo());
        this.setOpinion(reviewJpa.getOpinion());
        this.setFechaCreacion(reviewJpa.getFechaCreacion());
        this.setNota(reviewJpa.getNota());
    }
    public Usuario getUsuario() {
        if(usuario != null) return usuario;
        if(reviewJpa == null) return null;
        UsuarioJpa usuarioJpa = reviewJpa.getUsuarioJpa();

        if(usuarioJpa == null) return null;
        Usuario usuario1 = new Usuario(usuarioJpa);
        this.setUsuario(usuario1);
        return usuario1;

    }
*/

}
