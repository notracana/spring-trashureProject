package com.project.trashure.config.dao;

//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Repository
public class UserDao {
/*
    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new User("trashureteam@gmail.com",
                    "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
            new User("anacarton.dlc@gmail.com",
                    "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
    );

    public UserDetails findUserByEmail (String email){
        return APPLICATION_USERS.stream().filter(user -> user.getUsername().equals(email)).findFirst()
                .orElseThrow(()->  new UsernameNotFoundException("No se encontró ningún usuario"));

    }

 */
}
