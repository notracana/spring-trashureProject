package com.project.trashure.producto.domain;

import com.project.trashure.usuario.domain.UsuarioJpa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name="id_usuario")
    Integer idUsuario;
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
    @JoinColumn(name="id_usuario", updatable = false, insertable = false)
    private UsuarioJpa propietarioJpa;

    @Column(name="disponibilidad")
    private String disponibilidad;

    public ProductoJpa (Producto producto){
        if(producto == null) return;

        this.setIdProducto(producto.getIdProducto());
        this.setNombre(producto.getNombre());
        this.setDescripcion(producto.getDescripcion());
        this.setEstado(producto.getEstado());
        this.setImagen(producto.getImagen());
        //this.setPrecio(producto.getPrecio());
        //this.setCantidad(producto.getCantidad());
        //this.setIdUsuario(producto.getIdUsuario());
        this.setDisponibilidad(producto.getDisponibilidad());
        this.setIdUsuario(producto.getIdUsuario());
    }
}
