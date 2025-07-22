package com.eps.epsapp.controller;

import com.eps.epsapp.entity.Usuario;
import com.eps.epsapp.entity.Profesional;
import com.eps.epsapp.service.UsuarioService;
import com.eps.epsapp.repository.ProfesionalRepository; // ✅ importa el repo de Profesionales

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProfesionalRepository profesionalRepository; // ✅ inyectamos el repo

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id);
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario){
        Optional<Usuario> usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getLogin(), usuario.getContrasena());

        if (usuarioAutenticado.isPresent()) {
            Usuario u = usuarioAutenticado.get();

            // ✅ Preparamos el JSON personalizado
            Map<String, Object> response = new HashMap<>();
            response.put("idUsuario", u.getIdUsuario());
            response.put("login", u.getLogin());
            response.put("rol", u.getRol());
            response.put("tercero", u.getTercero());

            // ✅ Si es profesional, buscamos su idProfesional
            if ("Profesional".equals(u.getRol().getNombreRol())) {
                Profesional profesional = profesionalRepository.findByTercero(u.getTercero());
                if (profesional != null) {
                    response.put("profesional", Map.of("idProfesional", profesional.getIdProfesional()));
                }
            }

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
}
