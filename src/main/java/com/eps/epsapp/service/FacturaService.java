package com.eps.epsapp.service;

import com.eps.epsapp.entity.Factura;

import java.util.List;
import java.util.Optional;

public interface FacturaService {
    List<Factura> listarTodos();
    Optional<Factura> obtenerPorId(Integer id);
    Factura guardar(Factura factura);
    Factura actualizar(Integer id, Factura facturaActualizada);
    void eliminar(Integer id);
}
