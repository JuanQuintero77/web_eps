package com.eps.epsapp.service;

import com.eps.epsapp.entity.Afiliado;

import java.util.List;
import java.util.Optional;

public interface AfiliadoService {
    List<Afiliado> listarTodos();
    Optional<Afiliado> obtenerPorId(Integer id);
    Afiliado guardar(Afiliado afiliado);
    Afiliado actualizar(Integer id, Afiliado afiliado);
    void eliminar(Integer id);
}
