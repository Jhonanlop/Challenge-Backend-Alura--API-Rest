package com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.validaciones;

import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.ActualizarRespuesta;
import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.Respuesta;
import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.RespuestaRepository;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.Estado;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaDuplicada implements ValidarRespuestaActualizada{

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(ActualizarRespuesta data, Long respuestaId) {
        if (data.solucion()){
            Respuesta respuesta = respuestaRepository.getReferenceById(respuestaId);
            var topicoResuelto = topicoRepository.getReferenceById(respuesta.getTopico().getId());
            if (topicoResuelto.getEstado() == Estado.Closed){
                throw new ValidationException("Este topico ya esta solucionado.");
            }
        }
    }
}
