package com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.validaciones;


import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.CrearRespuesta;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.Estado;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaTopico implements ValidarRespuestaCreada {

    @Autowired
    private TopicoRepository repository;

    @Override
    public void validate(CrearRespuesta dato) {
        var topicoExiste = repository.existsById(dato.topicoId());

        if (!topicoExiste){
            throw new ValidationException("Este topico no existe.");
        }

        var topicoAbierto = repository.findById(dato.topicoId()).get().getEstado();

        if(topicoAbierto != Estado.Open){
            throw new ValidationException("Este topico no esta abierto.");
        }

    }
}
