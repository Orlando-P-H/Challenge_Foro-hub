package com.alura.foro.repository;

import com.alura.foro.model.Curso;
import com.alura.foro.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    boolean existsByNombre(String nombre);

}
