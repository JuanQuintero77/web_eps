package com.eps.epsapp.service;

import com.eps.epsapp.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> listarTodos();
    Optional<Rol> obtenerPorId(Integer id);
    Rol guardar(Rol rol);
    Rol actualizar(Integer id, Rol rolActualizado);
    void eliminar(Integer id);
}
