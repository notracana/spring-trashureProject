package com.project.trashure.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;

    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new User("trashureteam@gmail.com",
                    "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
            new User("anacarton.dlc@gmail.com",
                    "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
    );
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //Queremos que cualquier petición sea autenticada
        http.authorizeHttpRequests().
                anyRequest().authenticated().
                and().
                authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return null;
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return  new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return APPLICATION_USERS.stream().filter(user -> user.getUsername().equals(email)).findFirst()
                        .orElseThrow(()->  new UsernameNotFoundException("No se encontró ningún usuario"));
            }
        };
    }
}
