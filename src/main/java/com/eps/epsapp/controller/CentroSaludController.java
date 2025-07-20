package com.eps.epsapp.controller;

import com.eps.epsapp.entity.CentroSalud;
import com.eps.epsapp.service.CentroSaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/centros_salud")
public class CentroSaludController {

    @Autowired
    private CentroSaludService centroSaludService;

    @GetMapping
    public List<CentroSalud> listarTodos() {
        return centroSaludService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<CentroSalud> obtenerPorId(@PathVariable Integer id) {
        return centroSaludService.obtenerPorId(id);
    }

    @PostMapping
    public CentroSalud guardar(@RequestBody CentroSalud centroSalud) {
        return centroSaludService.guardar(centroSalud);
    }

    @PutMapping("/{id}")
    public CentroSalud actualizar(@PathVariable Integer id, @RequestBody CentroSalud centroSaludActualizado) {
        return centroSaludService.actualizar(id, centroSaludActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        centroSaludService.eliminar(id);
    }
}
