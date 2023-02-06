package com.project.trashure.config.controller;

import com.project.trashure.config.JwtUtils;
import com.project.trashure.config.controller.dto.input.PeticionAuthInputDTO;
import com.project.trashure.config.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v0/auth")
@AllArgsConstructor
public class AuthController {
    /*

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;

    private final JwtUtils jwtUtils;

    @PostMapping("/autenticacion")
    public ResponseEntity<String> autenticar(
            @RequestBody PeticionAuthInputDTO peticionAuth
    ) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(peticionAuth.getEmail(), peticionAuth.getPassword())
        );
        final UserDetails userDetails = userDao.findUserByEmail(peticionAuth.getEmail());

        if (userDetails != null){
           return ResponseEntity.ok(jwtUtils.generarToken(userDetails));
        }
        return ResponseEntity.status(400).body("Se ha producido un error en la autenticación.");
    }*/
}
