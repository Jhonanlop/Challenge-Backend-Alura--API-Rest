package com.alura.latam.Challenge_Back_End_Alura.domain.topico.validaciones;

import com.alura.latam.Challenge_Back_End_Alura.domain.topico.CrearTopico;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidarTopicoCreado{

    @Autowired
    private TopicoRepository topicoRepository;


    @Override
    public void validate(CrearTopico dato) {
        var topicoDuplicado = topicoRepository.existsByTituloAndMensaje(dato.titulo(), dato.mensaje());
        if(topicoDuplicado){
            throw new ValidationException("Este topico ya existe. Revisa /topicos/" + topicoRepository.findByTitulo(dato.titulo()).getId());

        }
    }
}