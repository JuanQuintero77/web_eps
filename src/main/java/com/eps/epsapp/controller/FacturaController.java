package com.eps.epsapp.controller;

import com.eps.epsapp.entity.Factura;
import com.eps.epsapp.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public List<Factura> listarTodas() {
        return facturaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Factura> obtenerPorId(@PathVariable Integer id) {
        return facturaService.obtenerPorId(id);
    }

    @PostMapping
    public Factura guardar(@RequestBody Factura factura) {
        return facturaService.guardar(factura);
    }

    @PutMapping("/{id}")
    public Factura actualizar(@PathVariable Integer id, @RequestBody Factura factura) {
        return facturaService.actualizar(id, factura);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        facturaService.eliminar(id);
    }
}
