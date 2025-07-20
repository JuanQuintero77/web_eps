package com.eps.epsapp.service;

import com.eps.epsapp.entity.Tercero;

import java.util.List;
import java.util.Optional;

public interface TerceroService {
    List<Tercero> listarTodos();
    Optional<Tercero> obtenerPorId(Integer id);
    Tercero guardar(Tercero tercero);
    Tercero actualizar(Integer id, Tercero terceroActualizado);
    void eliminar(Integer id);
}

