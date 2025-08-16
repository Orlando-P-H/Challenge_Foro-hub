package com.alura.foro.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosTopico(
    @NotBlank(message = " ! El titulo no puede estar vacio")
    String titulo,
    @NotBlank(message = "! El mensaje no puede ser vacio")
    String mensaje,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "! La fecha es necesaria")
    LocalDate fechaCreacion,
    @NotBlank(message = "! Se debe indicar el status")
    String status,
    @NotNull(message = "! El id del autor es obligatorio")
    @Min(value = 1, message = "! El id(autor) debe ser mayor a cero")
    Integer autor,
    @NotNull(message = "! El id del curso es obligatorio")
    @Min(value = 1, message = "! El id(curso) debe ser mayor a cero")
    Integer curso
) {

    public DatosTopico(Topico topico) {
        this(topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso());
    }
}
