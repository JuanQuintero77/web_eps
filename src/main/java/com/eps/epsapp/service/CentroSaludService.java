package com.eps.epsapp.service;

import com.eps.epsapp.entity.CentroSalud;

import java.util.List;
import java.util.Optional;

public interface CentroSaludService {
    List<CentroSalud> listarTodos();
    Optional<CentroSalud> obtenerPorId(Integer id);
    CentroSalud guardar(CentroSalud centroSalud);
    CentroSalud actualizar(Integer id, CentroSalud centroSaludActualizado);
    void eliminar(Integer id);
}
