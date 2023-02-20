package com.project.trashure.usuario.application;

import com.project.trashure.error.ErrorPropio;
import com.project.trashure.usuario.application.port.CreateUsuarioPort;
import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.domain.UsuarioJpa;
import com.project.trashure.usuario.infrastructure.repository.jpa.UsuarioRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class CreateUsuarioUseCase implements CreateUsuarioPort {

    private UsuarioRepositoryJpa usuarioRepositoryJpa;
    @Override
    public Usuario create(Usuario usuario) throws Exception {
        //primero se comprueba que el username y el email facilitados no es´ten registrados en la base de datos
        String username = usuario.getUsername();
        if(usuarioRepositoryJpa.findByUsername(username)!=null){

            throw new Exception("Ya existe un usuario registrado con ese nombre de usuario.");

        }
        String email = usuario.getEmail();
        if(usuarioRepositoryJpa.findByEmail(email)!=null){

            throw new Exception("Ya existe un usuario registrado con ese email.");

        }
        String passwordHashed = toHexString(obtenerSHA(usuario.getPassword()));
        usuario.setPassword(passwordHashed);
        UsuarioJpa usuarioJpa = new UsuarioJpa(usuario);
        System.out.println("comprobacion de que se guarda la contraseña " + usuarioJpa.getPassword());
        return new Usuario(usuarioRepositoryJpa.save(usuarioJpa));
    }

    //El método obtenerSHA usa MessageDigest para generar un hash con SHA256

        public static byte[] obtenerSHA(String password) throws NoSuchAlgorithmException
        {
            //Se crea una instancia de la clase MessageDigest para realizar el hashing usando el algoritmo SHA256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            //El método digest sirve para calcular el resumen de la contraseña (deuelve un array de bytes)
            return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        }

        //Con el método toHexString, al que le pasamos el array de bytes obtenido
        //necesita ser convertido a formato hexadecimal

        public static String toHexString(byte[] hash)
        {
            //Se convierte el array de bytes
            BigInteger number = new BigInteger(1, hash);

            //Se conviernte el digest en valor hexadecimal
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 32)
            {
                hexString.insert(0, '0');
            }

            //Se devuelve el valor encriptado, que se podrá guardar en la base de datos
            return hexString.toString();
        }

    }



