package com.alura.latam.Challenge_Back_End_Alura.domain.usuario;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.Role;


public record DetallesUsuario(
        Long id,
        String username,
        Role role,
        String nombre,
        String apellido,
        String email,
        Boolean enabled
) {

    public DetallesUsuario(Usuario usuario){
        this(usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
    }
}
