package com.eps.epsapp.controller;

import com.eps.epsapp.entity.PlanSalud;
import com.eps.epsapp.service.PlanSaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes-salud")
@CrossOrigin(origins = "*")
public class PlanSaludController {

    @Autowired
    private PlanSaludService planSaludService;

    @GetMapping
    public List<PlanSalud> listarTodos() {
        return planSaludService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<PlanSalud> obtenerPorId(@PathVariable Integer id) {
        return planSaludService.obtenerPorId(id);
    }

    @PostMapping
    public PlanSalud guardar(@RequestBody PlanSalud planSalud) {
        return planSaludService.guardar(planSalud);
    }

    @PutMapping("/{id}")
    public PlanSalud actualizar(@PathVariable Integer id, @RequestBody PlanSalud planSalud) {
        return planSaludService.actualizar(id, planSalud);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        planSaludService.eliminar(id);
    }
}
