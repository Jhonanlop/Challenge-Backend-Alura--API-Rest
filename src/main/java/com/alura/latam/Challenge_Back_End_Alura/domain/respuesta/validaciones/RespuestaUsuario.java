package com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.validaciones;

import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.CrearRespuesta;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaUsuario implements ValidarRespuestaCreada{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CrearRespuesta dato) {
        var usuarioExiste = repository.existsById(dato.usuarioId());

        if(!usuarioExiste){
            throw new ValidationException("Este usuario no existe");
        }

        var usuarioHabilitado = repository.findById(dato.usuarioId()).get().isEnabled();

        if(!usuarioHabilitado){
            throw new ValidationException("Este usuario no esta habilitado");
        }
    }
}
