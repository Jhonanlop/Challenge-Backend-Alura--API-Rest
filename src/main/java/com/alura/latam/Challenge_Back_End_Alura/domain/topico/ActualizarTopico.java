package com.alura.latam.Challenge_Back_End_Alura.domain.topico;

public record ActualizarTopico(
        String titulo,
        String mensaje,
        Estado estado,
        Long cursoId
) {
}
