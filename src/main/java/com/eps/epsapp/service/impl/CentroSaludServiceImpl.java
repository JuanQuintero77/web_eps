package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.CentroSalud;
import com.eps.epsapp.repository.CentroSaludRepository;
import com.eps.epsapp.service.CentroSaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CentroSaludServiceImpl implements CentroSaludService {

    @Autowired
    private CentroSaludRepository centroSaludRepository;

    @Override
    public List<CentroSalud> listarTodos() {
        return centroSaludRepository.findAll();
    }

    @Override
    public Optional<CentroSalud> obtenerPorId(Integer id) {
        return centroSaludRepository.findById(id);
    }

    @Override
    public CentroSalud guardar(CentroSalud centroSalud) {
        return centroSaludRepository.save(centroSalud);
    }

    @Override
    public CentroSalud actualizar(Integer id, CentroSalud centroSaludActualizado) {
        Optional<CentroSalud> optional = centroSaludRepository.findById(id);
        if (optional.isPresent()) {
            CentroSalud existente = optional.get();
            existente.setNombre(centroSaludActualizado.getNombre());
            existente.setDireccion(centroSaludActualizado.getDireccion());
            existente.setTelefono(centroSaludActualizado.getTelefono());
            return centroSaludRepository.save(existente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        centroSaludRepository.deleteById(id);
    }
}
