package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Factura;
import com.eps.epsapp.repository.FacturaRepository;
import com.eps.epsapp.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public List<Factura> listarTodos() {
        return facturaRepository.findAll();
    }

    @Override
    public Optional<Factura> obtenerPorId(Integer id) {
        return facturaRepository.findById(id);
    }

    @Override
    public Factura guardar(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public Factura actualizar(Integer id, Factura facturaActualizada) {
        Optional<Factura> optionalFactura = facturaRepository.findById(id);
        if (optionalFactura.isPresent()) {
            Factura facturaExistente = optionalFactura.get();
            facturaExistente.setFecha(facturaActualizada.getFecha());
            facturaExistente.setValorTotal(facturaActualizada.getValorTotal());
            facturaExistente.setEstadoPago(facturaActualizada.getEstadoPago());
            facturaExistente.setAfiliado(facturaActualizada.getAfiliado());
            facturaExistente.setCita(facturaActualizada.getCita());
            return facturaRepository.save(facturaExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        facturaRepository.deleteById(id);
    }
}
