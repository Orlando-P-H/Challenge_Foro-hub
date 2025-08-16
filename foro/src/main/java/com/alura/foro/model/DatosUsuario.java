package com.alura.foro.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosUsuario(
        @NotBlank(message = " ! El nombre no puede estar vacio")
        String nombre,
        @NotBlank(message = " ! El correo no puede estar vacio")
        String correo,
        @NotBlank(message = " ! La contrasena no puede estar vacia")
        String contrasena,
        @NotNull(message = "! El id del perfil es obligatorio")
        @Min(value = 1, message = "! El id(perfil) debe ser mayor a cero")
        Integer perfil
        ) {

    public DatosUsuario(Usuario usuario) {
        this(usuario.getNombre(),
             usuario.getCorreo(),
                usuario.getContrasena(),
                usuario.getPerfil()
                );
    }
}
