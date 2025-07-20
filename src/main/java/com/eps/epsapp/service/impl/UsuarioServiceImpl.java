package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Usuario;
import com.eps.epsapp.repository.UsuarioRepository;
import com.eps.epsapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(Integer id, Usuario usuarioActualizado) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuarioExistente = optionalUsuario.get();
            usuarioExistente.setLogin(usuarioActualizado.getLogin());
            usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
            usuarioExistente.setRol(usuarioActualizado.getRol());
            usuarioExistente.setTercero(usuarioActualizado.getTercero());
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> autenticarUsuario(String login, String contrasena) {
        return usuarioRepository.findByloginAndContrasena(login, contrasena);
    }
}

