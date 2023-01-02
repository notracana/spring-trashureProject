package com.project.trashure.review.domain;

import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.usuario.domain.UsuarioJpa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

//@Entity
//@Table(name="reviews")
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class ReviewJpa {
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_review")
    private String idReview;

    @Column (name = "autor")
    private String autor;

    @Column (name = "titulo")
    private String titulo;

    @Column (name = "opinion")
    private String opinion;

    @Column (name = "fecha_creacion")
    private Date fechaCreacion;

    @Column (name = "nota")
    private Double nota;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", updatable = false, insertable = false)
    private UsuarioJpa usuarioJpa;

    public ReviewJpa (Review review){
        if(review == null){return;}
        this.setIdReview(review.getIdReview());
        this.setAutor(review.getAutor());
        this.setTitulo(review.getTitulo());
        this.setOpinion(review.getOpinion());
        this.setFechaCreacion(review.getFechaCreacion());
        this.setNota(review.getNota());
    }*/

}
