package com.alura.foro.controller;

import com.alura.foro.model.Curso;
import com.alura.foro.model.DatosCurso;
import com.alura.foro.repository.CursoRepository;
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
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class cursoController {

    @Autowired
    private CursoRepository repo;

    //Agregar un curso
    @Transactional
    @PostMapping
    public void agregaCurso(@Valid @RequestBody DatosCurso datosCurso)
    {
        boolean existe = repo.existsByNombre(datosCurso.nombre());
        if (existe) {
            throw new IllegalArgumentException("! Ya existe un curso con ese nombre");
        }else{
            repo.save(new Curso(datosCurso));
        }

        System.out.println(datosCurso);

    }

    //Obtener todos los cursos
    @GetMapping()
    public Page<DatosCurso> obtenerCursos(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return repo.findAll(pageable).map(DatosCurso::new);
    }

    //Obtener curso por id.
    @GetMapping("/{id}")
    public Optional<DatosCurso> obtenerCurso(@PathVariable @NotNull @Positive Integer id)
    {
        return Optional.ofNullable(repo.findById(id).map(DatosCurso::new).
                orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "! Curso no encontrado !"
                                )));
    }

    //Actualizar un curso
    @Transactional
    @PutMapping("/{id}")
    public Curso actualizarCurso(@RequestBody @Valid DatosCurso datosCurso, @PathVariable @NotNull @Positive Integer id)
    {
        Curso existeCurso = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "! El curso no existe")
                );

        //Actualizamos los campos.
        existeCurso.setNombre(datosCurso.nombre());
        existeCurso.setCategoria(datosCurso.categoria());

        //Hacemos update
        return repo.save(existeCurso);

    }

    //Borrar un curso
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity borrarCurso(@PathVariable @NotNull @Positive Integer id)
    {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "! El curso no existe");
        }
        return ResponseEntity.noContent().build();
    }

}
