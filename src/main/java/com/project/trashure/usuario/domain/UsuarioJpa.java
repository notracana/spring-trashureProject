package com.project.trashure.usuario.domain;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.producto.domain.ProductoJpa;
import com.project.trashure.transaccion.domain.Transaccion;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class UsuarioJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
/*
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @GenericGenerator(
            name = "USUARIO_SEQ",
            strategy = "com.package com.project.trashure.sequences.SequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.INCREMENT_PARAM,
                            value = "1"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER,
                            value="USU"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER,
                            value = "%08d"
                    )
            }
    )*/
    @Column (name = "id_usuario")
    private int idUsuario;

    @Column(name="nombre")
    private String nombre;
    @Column (name = "apellidos")
    private String apellidos;

    @Column (name="username", unique = true)
    private String username;

    @Column (name="password")
    private String password;

    @Column (name="email", unique = true)
    private String email;


    @Column(name="direccion")
    private String direccion;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "telefono")
    private String telefono;


    //El campo userType sirve para distinguir entre los tipos de usuario
    @Column (name ="tipo_usuario")
    private String tipoUsuario;

    //Definimos una relación de uno a muchos entre usuario y productos y determinados que está mapeada por el campo
    //id_usuario, que es un campo definido en la clase Producto
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    List<ProductoJpa> productosSubidosJpa;

    //Relación de uno a muchos entre usuario y las compras, que es una lista de tipo Transaccion
    /*
    @OneToMany(mappedBy = "id_comprador")
    List<Transaccion> listaCompras;*/

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    List<TransaccionJpa> transaccionJpas;

    //Relación de uno a muchos entre usuario y las ventas, que es una lista de tipo Transaccion
    /*
    @OneToMany(mappedBy = "id_vendedor")
    List<Transaccion> listaVentas;*/


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "productos_favoritos",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name="id_producto")
    )
    List<ProductoJpa> productosFavoritosJpa;


    /*
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_review")
    List<ReviewJpa> reviewJpas;*/


    public UsuarioJpa(Usuario usuario) {
        this.setIdUsuario(usuario.getIdUsuario());
        this.setNombre(usuario.getNombre());
        this.setApellidos(usuario.getApellidos());
        this.setUsername(usuario.getUsername());
        this.setPassword(usuario.getPassword());
        this.setLocalidad(usuario.getLocalidad());
        this.setEmail(usuario.getEmail());
        this.setDireccion(usuario.getDireccion());
        this.setTelefono(usuario.getTelefono());
        this.setTipoUsuario(usuario.getTipoUsuario());

        List<Producto> productosSubidos = usuario.getProductosSubidos();
        updateProductosSubidos(productosSubidos);

        List<Transaccion> transaccionList = usuario.getTransacciones();
        updateTransacciones(transaccionList);

        List<Producto> productosFavoritos = usuario.getProductosFavoritos();
        updateProductosFavoritos(productosFavoritos);

        //List<Review> reviewList = usuario.getReviews();
        //updateReviews(reviewList);

        //MIRAR ESTO: CREO QUE ESTO NO SE DEBERÍA HACER ASÍ
        //this.setListaCompras(usuario.getListaCompras());
        //this.setListaVentas(usuario.getListaVentas());
    }

    private void updateProductosSubidos(List<Producto> productosSubidos){
        if(productosSubidos == null){
            this.setProductosSubidosJpa(new ArrayList<>());
            return;
        }
        List<ProductoJpa> productoJpas = productosSubidos.stream().map(ProductoJpa::new).collect(Collectors.toList());
        this.setProductosSubidosJpa(productoJpas);
    }

    private void updateProductosFavoritos(List<Producto> productosFavoritos){
        if(productosFavoritos == null){
            this.setProductosFavoritosJpa(new ArrayList<>());
            return;
        }
        List<ProductoJpa> productosFavsJpa = productosFavoritos.stream().map(ProductoJpa::new).collect(Collectors.toList());
        this.setProductosFavoritosJpa(productosFavsJpa);
    }

    private void updateTransacciones (List<Transaccion> transaccionList){
        if(transaccionList == null){
            this.setTransaccionJpas(new ArrayList<>());
            return;
        }
        List<TransaccionJpa> transaccionJpaList = transaccionList.stream().map(TransaccionJpa::new).collect(Collectors.toList());
        this.setTransaccionJpas(transaccionJpaList);
    }
    /*
    private void updateReviews (List<Review> reviewList){
        if(reviewList == null){
            this.setReviewJpas(new ArrayList<>());
            return;
        }
        List<ReviewJpa> reviewJpaList = reviewList.stream().map(ReviewJpa::new).collect(Collectors.toList());
        this.setReviewJpas(reviewJpaList);
    }*/

}
