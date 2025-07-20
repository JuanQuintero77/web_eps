package com.eps.epsapp.repository;

import com.eps.epsapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByloginAndContrasena(String login, String contrasena);
}
