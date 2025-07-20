package com.eps.epsapp.controller;

import com.eps.epsapp.entity.Afiliado;
import com.eps.epsapp.service.AfiliadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/afiliados")
public class AfiliadoController {

    @Autowired
    private AfiliadoService afiliadoService;

    @GetMapping
    public List<Afiliado> listar() {
        return afiliadoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Afiliado> obtener(@PathVariable Integer id) {
        return afiliadoService.obtenerPorId(id);
    }

    @PostMapping
    public Afiliado guardar(@RequestBody Afiliado afiliado) {
        return afiliadoService.guardar(afiliado);
    }

    @PutMapping("/{id}")
    public Afiliado actualizar(@PathVariable Integer id, @RequestBody Afiliado afiliado) {
        return afiliadoService.actualizar(id, afiliado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        afiliadoService.eliminar(id);
    }
}
