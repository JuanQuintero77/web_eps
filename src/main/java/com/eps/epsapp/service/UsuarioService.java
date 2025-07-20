package com.eps.epsapp.service;

import com.eps.epsapp.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listarTodos();
    Optional<Usuario> obtenerPorId(Integer id);
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Integer id, Usuario usuarioActualizado);
    void eliminar(Integer id);
    Optional<Usuario> autenticarUsuario(String login, String contrasena);
}
