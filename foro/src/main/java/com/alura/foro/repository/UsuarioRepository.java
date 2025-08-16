package com.alura.foro.repository;

import com.alura.foro.model.Topico;
import com.alura.foro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByNombre(String nombre);

    UserDetails findByNombre(String nombre);

}
