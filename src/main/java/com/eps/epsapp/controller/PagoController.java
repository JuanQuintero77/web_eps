package com.eps.epsapp.controller;

import com.eps.epsapp.entity.Pago;
import com.eps.epsapp.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> listarTodos() {
        return pagoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Pago> obtenerPorId(@PathVariable Integer id) {
        return pagoService.obtenerPorId(id);
    }

    @PostMapping
    public Pago guardar(@RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @PutMapping("/{id}")
    public Pago actualizar(@PathVariable Integer id, @RequestBody Pago pago) {
        return pagoService.actualizar(id, pago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
    }
}
