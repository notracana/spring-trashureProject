package com.project.trashure.usuario.domain;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.review.domain.Review;
import com.project.trashure.review.domain.ReviewJpa;
import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String username;

    private String password;
    private String email;

    private String direccion;

    private String localidad;
    private String telefono;


    //El campo userType sirve para distinguir entre los tipos de usuario
    private String tipoUsuario;

    List<Producto> productosSubidos;

    List<Transaccion> transaccionList;

    List<Review> reviewList;
    List<Transaccion> listaCompras;

    List<Transaccion> listaVentas;

    //List<Producto> listaFavs;

    private UsuarioJpa usuarioJpa;

    public Usuario (UsuarioJpa usuarioJpa){
        if(usuarioJpa == null) return;

        this.setIdUsuario(usuarioJpa.getIdUsuario());
        this.setNombre(usuarioJpa.getNombre());
        this.setApellidos(usuarioJpa.getApellidos());
        this.setUsername(usuarioJpa.getUsername());
        this.setPassword(usuarioJpa.getPassword());
        this.setLocalidad(usuarioJpa.getLocalidad());
        this.setEmail(usuarioJpa.getEmail());
        this.setDireccion(usuarioJpa.getDireccion());
        this.setTelefono(usuarioJpa.getTelefono());
        this.setTipoUsuario(usuarioJpa.getTipoUsuario());
        //this.setProductosSubidos(usuarioJpa.getProductosSubidos());
        //this.setListaCompras(usuarioJpa.getListaCompras());
        //this.setListaVentas(usuarioJpa.getListaVentas());
        //this.setListaFavs(usuarioJpa.getListFavs());
    }

    public List<Producto> getProductosSubidos(){
        if(productosSubidos !=null) return productosSubidos;
        if(usuarioJpa == null) return new ArrayList<>();

        List<ProductoJpa> productosSubidosJpa = usuarioJpa.getProductosSubidosJpa();
        if (productosSubidosJpa == null) return new ArrayList<>();

        List<Producto> productosSubidos = productosSubidosJpa.stream().map(Producto::new).collect(Collectors.toList());
        this.setProductosSubidos(productosSubidos);
        return this.productosSubidos;

    }

    public List<Transaccion> getTransacciones(){
        if(transaccionList != null) return transaccionList;
        if(usuarioJpa == null) return new ArrayList<>();

        List<TransaccionJpa> transaccionJpaList = usuarioJpa.getTransaccionJpas();
        if(transaccionJpaList == null) return new ArrayList<>();

        List <Transaccion> transacciones = transaccionJpaList.stream().map(Transaccion::new).collect(Collectors.toList());
        this.setTransaccionList(transacciones);
        return this.transaccionList;
    }

    /*
    public List<Review> getReviews(){
        if(reviewList != null) return reviewList;
        if(usuarioJpa == null) return new ArrayList<>();

        List<ReviewJpa> reviewJpaList = usuarioJpa.getReviewJpas();
        if(reviewJpaList == null) return new ArrayList<>();

        List <Review> reviews = reviewJpaList.stream().map(Review::new).collect(Collectors.toList());
        this.setReviewList(reviews);
        return this.reviewList;
    }*/



}
