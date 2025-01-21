package com.alura.latam.Challenge_Back_End_Alura.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearRespuesta(@NotBlank String mensaje, @NotNull Long usuarioId, @NotNull Long topicoId) {
}
