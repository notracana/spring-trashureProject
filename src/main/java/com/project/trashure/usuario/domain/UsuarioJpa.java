package com.project.trashure.usuario.domain;

import com.project.trashure.producto.domain.Producto;
import com.project.trashure.transaccion.domain.Transaccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class UsuarioJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_usuario")
    private String idUsuario;

    @Column(name="nombre")
    private String nombre;
    @Column (name = "apellidos")
    private String apellidos;

    @Column (name="username")
    private String username;

    @Column (name="password")
    private String password;

    @Column (name="email")
    private String email;


    @Column(name="direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;


    //El campo userType sirve para distinguir entre los tipos de usuario
    @Column (name ="tipo_usuario")
    private String tipoUsuario;

    //Definimos una relación de uno a muchos entre usuario y productos y determinados que está mapeada por el campo
    //id_usuario, que es un campo definido en la clase Producto
    @OneToMany(mappedBy = "idUsuario" )
    List<Producto> listaProductos;

    //Relación de uno a muchos entre usuario y las compras, que es una lista de tipo Transaccion
    @OneToMany(mappedBy = "id_comprador")
    List<Transaccion> listaCompras;

    //Relación de uno a muchos entre usuario y las ventas, que es una lista de tipo Transaccion
    @OneToMany(mappedBy = "id_vendedor")
    List<Transaccion> listaVentas;


    public UsuarioJpa(Usuario usuario) {
        this.setIdUsuario(usuario.getIdUsuario());
        this.setNombre(usuario.getNombre());
        this.setApellidos(usuario.getApellidos());
        this.setUsername(usuario.getUsername());
        this.setPassword(usuario.getPassword());
        this.setEmail(usuario.getEmail());
        this.setDireccion(usuario.getDireccion());
        this.setTelefono(usuario.getTelefono());
        this.setTipoUsuario(usuario.getTipoUsuario());

        //MIRAR ESTO: CREO QUE ESTO NO SE DEBERÍA HACER ASÍ
        this.setListaProductos(usuario.getListaProductos());
        this.setListaCompras(usuario.getListaCompras());
        this.setListaVentas(usuario.getListaVentas());
    }
}
