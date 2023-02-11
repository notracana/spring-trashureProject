package com.project.trashure.review.domain;

import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import com.project.trashure.usuario.domain.UsuarioJpa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_review")
    private Integer idReview;

    @Column (name = "id_transaccion")
    private Integer idTransaccion;

    @Column (name = "id_autor")
    private Integer idUsuarioAutor;

    @Column (name = "id_destinatario")
    private Integer idUsuarioDestinatario;




    /*@Column (name = "titulo")
    private String titulo;*/

    @Column (name = "opinion")
    private String opinion;

    @Column (name = "fecha_creacion")
    private Date fechaCreacion;

    /*@Column (name = "nota")
    private Double nota;*/

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", updatable = false, insertable = false)
    private UsuarioJpa usuarioJpa;*/

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name ="id_transaccion", updatable = false, insertable = false)
    private TransaccionJpa transaccionJpa;


    public ReviewJpa (Review review){
        if(review == null){return;}
        this.setIdReview(review.getIdReview());
        this.setIdTransaccion(review.getIdTransaccion());
        this.setIdUsuarioAutor(review.getIdUsuarioAutor());
        this.setIdUsuarioDestinatario(review.getIdUsuarioDestinatario());
        //this.setTitulo(review.getTitulo());
        this.setOpinion(review.getOpinion());
        this.setFechaCreacion(review.getFechaCreacion());
        this.setTransaccionJpa(new TransaccionJpa(review.getTransaccion()));
        //this.setNota(review.getNota());
    }

}
