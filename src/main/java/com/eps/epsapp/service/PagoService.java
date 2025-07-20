package com.eps.epsapp.service;

import com.eps.epsapp.entity.Pago;

import java.util.List;
import java.util.Optional;

public interface PagoService {
    List<Pago> listarTodos();
    Optional<Pago> obtenerPorId(Integer id);
    Pago guardar(Pago pago);
    Pago actualizar(Integer id, Pago pagoActualizado);
    void eliminar(Integer id);
}
