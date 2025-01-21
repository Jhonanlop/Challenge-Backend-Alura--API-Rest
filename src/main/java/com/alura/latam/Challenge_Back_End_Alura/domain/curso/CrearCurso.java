package com.alura.latam.Challenge_Back_End_Alura.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearCurso(@NotBlank String name, @NotNull Categoria categoria) {
}
