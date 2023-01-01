package com.project.trashure.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class WebSecurityConfig {

    UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("api/v0/admin/**").hasRole("ADMIN")
                .requestMatchers("api/v0/productos/**").hasAnyRole("ADMIN", "USER")
                .and().formLogin().loginPage("api/v0/usuarios/logIn")
                .permitAll().defaultSuccessUrl("api/v0/usuarios/iniciarSesion");

        return http.build();
    }

    /*
    In antMatchers() (as well as mvcMathcers() and regexMatchers()) have been deprecated and removed with Spring Security 6.0
Overloaded method requesMatchers() was provided as a uniform mean for securing requests.
 It facilitates all the functionality of the configuration methods that have been removed from the API.

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            .requesMatchers("/vertretungsplan").hasAnyRole("SCHUELER", "LEHRER", "VERWALTUNG")
            .anyRequest().authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/login")
            .permitAll()
        )
        .logout((logout) -> logout.permitAll());

    return http.build();
}*/

}
