package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Afiliado;
import com.eps.epsapp.repository.AfiliadoRepository;
import com.eps.epsapp.service.AfiliadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AfiliadoServiceImpl implements AfiliadoService {

    @Autowired
    private AfiliadoRepository afiliadoRepository;

    @Override
    public List<Afiliado> listarTodos() {
        return afiliadoRepository.findAll();
    }

    @Override
    public Optional<Afiliado> obtenerPorId(Integer id) {
        return afiliadoRepository.findById(id);
    }

    @Override
    public Afiliado guardar(Afiliado afiliado) {
        return afiliadoRepository.save(afiliado);
    }

    @Override
    public Afiliado actualizar(Integer id, Afiliado afiliadoActualizado) {
        Optional<Afiliado> optionalAfiliado = afiliadoRepository.findById(id);
        if (optionalAfiliado.isPresent()) {
            Afiliado afiliadoExistente = optionalAfiliado.get();
            afiliadoExistente.setFechaAfiliacion(afiliadoActualizado.getFechaAfiliacion());
            afiliadoExistente.setTercero(afiliadoActualizado.getTercero());
            afiliadoExistente.setPlanSalud(afiliadoActualizado.getPlanSalud());
            return afiliadoRepository.save(afiliadoExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        afiliadoRepository.deleteById(id);
    }
}
