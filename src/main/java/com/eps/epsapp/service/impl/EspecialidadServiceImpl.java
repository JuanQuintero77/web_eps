package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Especialidad;
import com.eps.epsapp.repository.EspecialidadRepository;
import com.eps.epsapp.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }

    @Override
    public Optional<Especialidad> obtenerPorId(Integer id) {
        return especialidadRepository.findById(id);
    }

    @Override
    public Especialidad guardar(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    @Override
    public Especialidad actualizar(Integer id, Especialidad especialidadActualizada) {
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findById(id);
        if (optionalEspecialidad.isPresent()) {
            Especialidad especialidadExistente = optionalEspecialidad.get();
            especialidadExistente.setNombreEspecialidad(especialidadActualizada.getNombreEspecialidad());
            return especialidadRepository.save(especialidadExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        especialidadRepository.deleteById(id);
    }
}
