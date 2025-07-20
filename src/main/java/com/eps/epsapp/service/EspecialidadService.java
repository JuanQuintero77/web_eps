package com.eps.epsapp.service;

import com.eps.epsapp.entity.Especialidad;

import java.util.List;
import java.util.Optional;

public interface EspecialidadService {
    List<Especialidad> listarTodas();
    Optional<Especialidad> obtenerPorId(Integer id);
    Especialidad guardar(Especialidad especialidad);
    Especialidad actualizar(Integer id, Especialidad especialidadActualizada);
    void eliminar(Integer id);
}
