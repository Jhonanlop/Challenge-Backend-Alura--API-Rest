package com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.validaciones;

import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.ActualizarRespuesta;

public interface ValidarRespuestaActualizada {

    public void validate(ActualizarRespuesta dato, Long respuestaId);
}
