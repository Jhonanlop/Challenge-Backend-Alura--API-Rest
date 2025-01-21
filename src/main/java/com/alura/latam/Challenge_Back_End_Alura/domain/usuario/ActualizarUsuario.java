package com.alura.latam.Challenge_Back_End_Alura.domain.usuario;

import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.Role;

public record ActualizarUsuario(
        String password,
        Role role,
        String nombre,
        String apellido,
        String email,
        Boolean enabled
) {
}
