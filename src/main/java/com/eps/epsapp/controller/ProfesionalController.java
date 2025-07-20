package com.eps.epsapp.controller;

import com.eps.epsapp.entity.Profesional;
import com.eps.epsapp.service.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalController {

    @Autowired
    private ProfesionalService profesionalService;

    @GetMapping
    public List<Profesional> listarTodos() {
        return profesionalService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesional> obtenerPorId(@PathVariable Integer id) {
        Optional<Profesional> profesional = profesionalService.obtenerPorId(id);
        return profesional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Profesional guardar(@RequestBody Profesional profesional) {
        return profesionalService.guardar(profesional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesional> actualizar(@PathVariable Integer id, @RequestBody Profesional profesionalActualizado) {
        Profesional actualizado = profesionalService.actualizar(id, profesionalActualizado);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        profesionalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
