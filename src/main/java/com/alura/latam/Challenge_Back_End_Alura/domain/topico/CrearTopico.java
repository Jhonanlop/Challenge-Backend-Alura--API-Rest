package com.alura.latam.Challenge_Back_End_Alura.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Long usuarioId,
        @NotNull Long cursoId
) {
}
