package com.project.trashure.usuario.infrastructure.repository;

import com.project.trashure.usuario.domain.Usuario;
import com.project.trashure.usuario.infrastructure.repository.port.FindUsuarioPort;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor

public class UserDetailsRepository{

/*
public class UserDetailsRepository implements UserDetailsService {

    /*

    private FindUsuarioPort findUsuarioPort;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = findUsuarioPort.findByUsername(username);
        //Corroboramos que el usuario esté en la base de datos
        if(usuario!= null){
            httpSession.setAttribute("idUsuario", usuario.getIdUsuario());
            //Se devuelve un objeto de tipo UserDetails
            List<String> roles = new ArrayList<>();
            roles.add(usuario.getTipoUsuario());
            List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
            UserDetails userDetails = new User(usuario.getUsername(),bCryptPasswordEncoder.encode(usuario.getPassword()),authorities);

            return userDetails;
        }
        else{
            throw new UsernameNotFoundException("No existe ningún usuario con ese username.");
        }
    }*/
}
