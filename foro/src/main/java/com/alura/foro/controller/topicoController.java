package com.alura.foro.controller;

import com.alura.foro.model.DatosTopico;
import com.alura.foro.model.Topico;
import com.alura.foro.repository.TopicoRepository;
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
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class topicoController {

    @Autowired
    private TopicoRepository repo;


    //Agregar un topico
    @Transactional
    @PostMapping
    public void agregaTopico(@Valid @RequestBody DatosTopico datosTopico)
    {
        boolean existe = repo.existsByTituloAndMensaje(datosTopico.titulo(), datosTopico.mensaje());
        if (existe) {
            throw new IllegalArgumentException("Ya existe un tópico con ese título y mensaje.");
        }else{
            repo.save(new Topico(datosTopico));
        }

        System.out.println(datosTopico);

    }

    //Obtener todos los topicos
    @GetMapping()
    public Page<DatosTopico> obtenerTopicos(
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return repo.findAll(pageable).map(DatosTopico::new);
    }

    //Obtener topico por id.
    @GetMapping("/{id}")
    public Optional<DatosTopico> obtenerTopico(@PathVariable @NotNull @Positive Integer id)
    {
        return Optional.ofNullable(repo.findById(id).map(DatosTopico::new).
                orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "! Topico no encontrado !"
                                )));
    }

    //Actualizar un topico
    @Transactional
    @PutMapping("/{id}")
    public Topico actualizarTopico(@RequestBody @Valid DatosTopico datosTopico, @PathVariable @NotNull @Positive Integer id)
    {
        Topico existeTopico = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "! El tópico no existe")
                );

        //Actualizamos los campos.
        existeTopico.setTitulo(datosTopico.titulo());
        existeTopico.setMensaje(datosTopico.mensaje());
        existeTopico.setFecha(datosTopico.fechaCreacion());
        existeTopico.setStatus(datosTopico.status());
        existeTopico.setAutor(datosTopico.autor());
        existeTopico.setCurso(datosTopico.curso());

        //Hacemos update
        return repo.save(existeTopico);

    }

    //Borrar un topico
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity borrarTopico(@PathVariable @NotNull @Positive Integer id)
    {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "! El topico no existe");
        }

        return ResponseEntity.noContent().build();

    }

}
