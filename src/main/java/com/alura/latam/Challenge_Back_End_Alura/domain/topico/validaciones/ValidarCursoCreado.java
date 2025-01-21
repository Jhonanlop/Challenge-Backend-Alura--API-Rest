package com.alura.latam.Challenge_Back_End_Alura.domain.topico.validaciones;

import com.alura.latam.Challenge_Back_End_Alura.domain.curso.CursoRepository;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.CrearTopico;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoCreado implements ValidarTopicoCreado{

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(CrearTopico dato) {
        var ExisteCurso = repository.existsById(dato.cursoId());
        if(!ExisteCurso){
            throw new ValidationException("Este curso no existe.");
        }

        var cursoHabilitado = repository.findById(dato.cursoId()).get().getActivo();
        if(!cursoHabilitado){
            throw new ValidationException("Este curso no esta disponible en este momento.");
        }
    }
}