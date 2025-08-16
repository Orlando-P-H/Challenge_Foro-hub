package com.alura.foro.controller;

import com.alura.foro.model.DatosUsuario;
import com.alura.foro.model.Usuario;
import com.alura.foro.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class usuarioController {

    @Autowired
    private UsuarioRepository repo;

    //Agregar un topico
    @Transactional
    @PostMapping
    public void agregaUsuario(@Valid @RequestBody DatosUsuario datosUsuario)
    {
        boolean existe = repo.existsByNombre(datosUsuario.nombre());
        if (existe) {
            throw new IllegalArgumentException("! Ya existe un usuario con ese nombre");
        }else{
            repo.save(new Usuario(datosUsuario));
        }

        System.out.println(datosUsuario);

    }

    //Obtener todos los usuarios
    @GetMapping()
    public Page<DatosUsuario> obtenerUsuarios(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return repo.findAll(pageable).map(DatosUsuario::new);
    }

    //Obtener usuario por id.
    @GetMapping("/{id}")
    public Optional<DatosUsuario> obtenerUsuario(@PathVariable @NotNull @Positive Integer id)
    {
        return Optional.ofNullable(repo.findById(id).map(DatosUsuario::new).
                orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "! Usuario no encontrado !"
                                )));
    }

    //Actualizar un usuario
    @Transactional
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@RequestBody @Valid DatosUsuario datosUsuario, @PathVariable @NotNull @Positive Integer id)
    {
        Usuario existeUsuario = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "! El usuario no existe")
                );

        //Actualizamos los campos.
        existeUsuario.setNombre(datosUsuario.nombre());
        existeUsuario.setCorreo(datosUsuario.correo());
        existeUsuario.setContrasena(datosUsuario.contrasena());
        existeUsuario.setPerfil(datosUsuario.perfil());

        //Hacemos update
        return repo.save(existeUsuario);

    }

    //Borrar un usuario
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity borrarUsuario(@PathVariable @NotNull @Positive Integer id)
    {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "! El usuario no existe");
        }

        return ResponseEntity.noContent().build();

    }

}
