package com.eps.epsapp.controller;

import com.eps.epsapp.entity.HistorialClinico;
import com.eps.epsapp.service.HistorialClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historiales-clinicos")
public class HistorialClinicoController {

    @Autowired
    private HistorialClinicoService historialClinicoService;

    @GetMapping
    public List<HistorialClinico> listarTodos() {
        return historialClinicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<HistorialClinico> obtenerPorId(@PathVariable Integer id) {
        return historialClinicoService.obtenerPorId(id);
    }

    @PostMapping
    public HistorialClinico guardar(@RequestBody HistorialClinico historialClinico) {
        return historialClinicoService.guardar(historialClinico);
    }

    @PutMapping("/{id}")
    public HistorialClinico actualizar(@PathVariable Integer id, @RequestBody HistorialClinico historialActualizado) {
        return historialClinicoService.actualizar(id, historialActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        historialClinicoService.eliminar(id);
    }
}
