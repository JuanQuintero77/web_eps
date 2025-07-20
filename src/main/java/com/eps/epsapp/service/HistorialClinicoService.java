package com.eps.epsapp.service;

import com.eps.epsapp.entity.HistorialClinico;

import java.util.List;
import java.util.Optional;

public interface HistorialClinicoService {
    List<HistorialClinico> listarTodos();
    Optional<HistorialClinico> obtenerPorId(Integer id);
    HistorialClinico guardar(HistorialClinico historialClinico);
    HistorialClinico actualizar(Integer id, HistorialClinico historialClinicoActualizado);
    void eliminar(Integer id);
}
