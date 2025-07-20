package com.eps.epsapp.service;

import com.eps.epsapp.entity.Profesional;

import java.util.List;
import java.util.Optional;

public interface ProfesionalService {
    List<Profesional> listarTodos();
    Optional<Profesional> obtenerPorId(Integer id);
    Profesional guardar(Profesional profesional);
    Profesional actualizar(Integer id, Profesional profesionalActualizado);
    void eliminar(Integer id);
}
