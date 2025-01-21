package com.alura.latam.Challenge_Back_End_Alura.controller;

import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.AutenticacionUsuario;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.Usuario;
import com.alura.latam.Challenge_Back_End_Alura.infra.security.JWTtoken;
import com.alura.latam.Challenge_Back_End_Alura.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutheticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<JWTtoken> autenticarUsuario(@RequestBody @Valid AutenticacionUsuario autenticacionUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(autenticacionUsuario.username(),
                autenticacionUsuario.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new JWTtoken(JWTtoken));
    }
}
