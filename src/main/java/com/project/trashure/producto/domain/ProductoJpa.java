package com.project.trashure.producto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.trashure.transaccion.domain.TransaccionJpa;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import jakarta.persistence.*;
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
@Entity
@Table(name="productos")
public class ProductoJpa {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    /*
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTO_SEQ")
    @GenericGenerator(
            name = "PRODUCTO_SEQ",
            strategy = "com.package com.project.trashure.sequences.SequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.INCREMENT_PARAM,
                            value = "1"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER,
                            value="PRO"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER,
                            value = "%08d"
                    )
            }
    )*/
    @Column(name="id_producto")
    private Integer idProducto;

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="estado")
    private String estado;

    @Column(name="categoria")
    private String categoria;

    @Column(name="id_usuario")
    private Integer idUsuario;
    @Column(name="imagen")
    private String imagen;

    //El campo price realmente hace referencia al tipo de venta al que está sujeto
    //porque el usuario puede elegir la modalidad de intercambiar o simplemente regalar el producto
    /*@Column(name="precio")
    private String precio;

    @Column(name="cantidad")
    private Integer cantidad;

    @Column(name = "id_usuario")
    private String idUsuario;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="id_usuario", updatable = false, insertable = false)
    private UsuarioJpa propietarioJpa;

    /*
    @JoinTable(
            name = "rel_usuario_producto_favorito",
            joinColumns = @JoinColumn(name = "id_producto", nullable = false),
            inverseJoinColumns = @JoinColumn(name="id_usuario", nullable = false)
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<UsuarioJpa> usuariosJpa;*/

    /*
    @ManyToMany(mappedBy = "productosFavoritosJpa")
    List<UsuarioJpa> favoritosDe;*/


    /*@JoinTable(
            name = "rel_usuario_producto",
            joinColumns = @JoinColumn(name = "id_producto", nullable=false),
            inverseJoinColumns = @JoinColumn(name="id_usuario", nullable = false)
    )*/
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "productosFavoritosJpa")
    List<UsuarioJpa> usuarios;
    @Column(name="disponibilidad")
    private String disponibilidad;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="id_producto", insertable = false, updatable = false)
    private List<TransaccionJpa> transaccionJpa;

    public ProductoJpa (Producto producto){
        if(producto == null) return;

        this.setIdProducto(producto.getIdProducto());
        this.setIdUsuario(producto.getIdUsuario());
        this.setNombre(producto.getNombre());
        this.setDescripcion(producto.getDescripcion());
        this.setEstado(producto.getEstado());
        this.setCategoria(producto.getCategoria());
        this.setImagen(producto.getImagen());
        //this.setFavoritosDe(producto.getFavoritosDe());
        //this.setPrecio(producto.getPrecio());
        //this.setCantidad(producto.getCantidad());
        //this.setIdUsuario(producto.getIdUsuario());
        this.setDisponibilidad(producto.getDisponibilidad());

        /*
        if(producto.getPropietario() != null){
            Usuario u = producto.getPropietario();
            updatePropietario(u);
            this.setIdUsuario(u.getIdUsuario());
        }*/
        if(producto.getUsuarios() != null && !producto.getUsuarios().isEmpty()){
            List<Usuario> usuarios = producto.getUsuarios();
            updateUsuarios(usuarios);
        }
    }

    private void updateUsuarios(List<Usuario> usuarios){
        if(usuarios == null){
            this.setUsuarios(new ArrayList<>());
            return;
        }
        this.setUsuarios(usuarios.stream().map(UsuarioJpa::new).collect(Collectors.toList()));
    }

    private void updatePropietario(Usuario propietario){
        if(propietario == null){
            this.setPropietarioJpa(null);

            return;
        }
        this.setPropietarioJpa(new UsuarioJpa(propietario));
    }
}
