package com.eps.epsapp.service;

import com.eps.epsapp.entity.PlanSalud;

import java.util.List;
import java.util.Optional;

public interface PlanSaludService {
    List<PlanSalud> listarTodos();
    Optional<PlanSalud> obtenerPorId(Integer id);
    PlanSalud guardar(PlanSalud planSalud);
    PlanSalud actualizar(Integer id, PlanSalud planSaludActualizado);
    void eliminar(Integer id);
}
