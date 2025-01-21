package com.alura.latam.Challenge_Back_End_Alura.domain.topico.validaciones;

import com.alura.latam.Challenge_Back_End_Alura.domain.topico.CrearTopico;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoUsuario implements ValidarTopicoCreado{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CrearTopico dato) {
        var existeUsuario = repository.existsById(dato.usuarioId());
        if(!existeUsuario){
            throw new ValidationException("Este usuario no existe");
        }

        var usuarioHabilitado = repository.findById(dato.usuarioId()).get().getEnabled();
        if(!usuarioHabilitado){
            throw new ValidationException("Este usuario fue deshabiliado.");
        }

    }
}