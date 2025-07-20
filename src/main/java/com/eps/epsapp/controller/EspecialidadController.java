package com.eps.epsapp.controller;

import com.eps.epsapp.entity.Especialidad;
import com.eps.epsapp.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public List<Especialidad> listarTodas() {
        return especialidadService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Especialidad> obtenerPorId(@PathVariable Integer id) {
        return especialidadService.obtenerPorId(id);
    }

    @PostMapping
    public Especialidad guardar(@RequestBody Especialidad especialidad) {
        return especialidadService.guardar(especialidad);
    }

    @PutMapping("/{id}")
    public Especialidad actualizar(@PathVariable Integer id, @RequestBody Especialidad especialidad) {
        return especialidadService.actualizar(id, especialidad);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        especialidadService.eliminar(id);
    }
}
