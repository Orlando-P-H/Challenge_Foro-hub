package com.alura.foro.repository;

import com.alura.foro.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Integer> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);


}
