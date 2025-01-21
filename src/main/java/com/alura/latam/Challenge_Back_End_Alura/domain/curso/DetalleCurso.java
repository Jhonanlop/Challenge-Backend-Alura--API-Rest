package com.alura.latam.Challenge_Back_End_Alura.domain.curso;

public record DetalleCurso(Long id, String name, Categoria categoria, Boolean activo) {

    public DetalleCurso(Curso curso) {
        this(curso.getId(), curso.getName(), curso.getCategoria(), curso.getActivo());
    }
}
