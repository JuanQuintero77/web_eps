package com.eps.epsapp.controller;

import com.eps.epsapp.entity.Tercero;
import com.eps.epsapp.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/terceros")
public class TerceroController {

    @Autowired
    private TerceroService terceroService;

    @GetMapping
    public List<Tercero> listarTodos() {
        return terceroService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Tercero> obtenerPorId(@PathVariable Integer id) {
        return terceroService.obtenerPorId(id);
    }

    @PostMapping
    public Tercero guardar(@RequestBody Tercero tercero) {
        return terceroService.guardar(tercero);
    }

    @PutMapping("/{id}")
    public Tercero actualizar(@PathVariable Integer id, @RequestBody Tercero tercero) {
        return terceroService.actualizar(id, tercero);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        terceroService.eliminar(id);
    }
}
