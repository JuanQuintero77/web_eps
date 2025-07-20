package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Pago;
import com.eps.epsapp.repository.PagoRepository;
import com.eps.epsapp.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    @Override
    public Optional<Pago> obtenerPorId(Integer id) {
        return pagoRepository.findById(id);
    }

    @Override
    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public Pago actualizar(Integer id, Pago pagoActualizado) {
        Optional<Pago> optionalPago = pagoRepository.findById(id);
        if (optionalPago.isPresent()) {
            Pago pagoExistente = optionalPago.get();
            pagoExistente.setFactura(pagoActualizado.getFactura());
            pagoExistente.setFechaPago(pagoActualizado.getFechaPago());
            pagoExistente.setValorPagado(pagoActualizado.getValorPagado());
            pagoExistente.setMedioPago(pagoActualizado.getMedioPago());
            return pagoRepository.save(pagoExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        pagoRepository.deleteById(id);
    }
}
