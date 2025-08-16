package com.alura.foro.model;

import jakarta.validation.constraints.NotBlank;

public record DatosCurso(
        @NotBlank(message = " ! El nombre no puede estar vacio")
        String nombre,
        @NotBlank(message = " ! La categoria no puede estar vacia")
        String categoria
) {

    public DatosCurso(Curso curso) {
        this(curso.getNombre(),
                curso.getCategoria()
        );
    }

}
