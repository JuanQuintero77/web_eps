package com.eps.epsapp.controller;

import com.eps.epsapp.entity.Rol;
import com.eps.epsapp.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<Rol> listarTodos() {
        return rolService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Rol> obtenerPorId(@PathVariable Integer id) {
        return rolService.obtenerPorId(id);
    }

    @PostMapping
    public Rol guardar(@RequestBody Rol rol) {
        return rolService.guardar(rol);
    }

    @PutMapping("/{id}")
    public Rol actualizar(@PathVariable Integer id, @RequestBody Rol rolActualizado) {
        return rolService.actualizar(id, rolActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        rolService.eliminar(id);
    }
}
