package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Profesional;
import com.eps.epsapp.repository.ProfesionalRepository;
import com.eps.epsapp.service.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesionalServiceImpl implements ProfesionalService {

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Override
    public List<Profesional> listarTodos() {
        return profesionalRepository.findAll();
    }

    @Override
    public Optional<Profesional> obtenerPorId(Integer id) {
        return profesionalRepository.findById(id);
    }

    @Override
    public Profesional guardar(Profesional profesional) {
        return profesionalRepository.save(profesional);
    }

    @Override
    public Profesional actualizar(Integer id, Profesional profesionalActualizado) {
        Optional<Profesional> optionalProfesional = profesionalRepository.findById(id);
        if (optionalProfesional.isPresent()) {
            Profesional profesionalExistente = optionalProfesional.get();
            profesionalExistente.setTercero(profesionalActualizado.getTercero());
            profesionalExistente.setCentroSalud(profesionalActualizado.getCentroSalud()); // ← FALTABA
            profesionalExistente.setEspecialidad(profesionalActualizado.getEspecialidad());
            return profesionalRepository.save(profesionalExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        profesionalRepository.deleteById(id);
    }
}
