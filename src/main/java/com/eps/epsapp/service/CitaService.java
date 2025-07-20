package com.eps.epsapp.service;

import com.eps.epsapp.entity.Cita;

import java.util.List;
import java.util.Optional;

public interface CitaService {

    List<Cita> listarTodos(); // ✅ Nombre correcto

    Optional<Cita> obtenerPorId(Integer id);

    Cita guardar(Cita cita);

    Cita actualizar(Integer id, Cita citaActualizada);

    void eliminar(Integer id);
}
