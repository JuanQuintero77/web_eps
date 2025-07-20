package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.HistorialClinico;
import com.eps.epsapp.repository.HistorialClinicoRepository;
import com.eps.epsapp.service.HistorialClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialClinicoServiceImpl implements HistorialClinicoService {

    @Autowired
    private HistorialClinicoRepository historialClinicoRepository;

    @Override
    public List<HistorialClinico> listarTodos() {
        return historialClinicoRepository.findAll();
    }

    @Override
    public Optional<HistorialClinico> obtenerPorId(Integer id) {
        return historialClinicoRepository.findById(id);
    }

    @Override
    public HistorialClinico guardar(HistorialClinico historialClinico) {
        return historialClinicoRepository.save(historialClinico);
    }

    @Override
    public HistorialClinico actualizar(Integer id, HistorialClinico historialClinicoActualizado) {
        Optional<HistorialClinico> optionalHistorial = historialClinicoRepository.findById(id);
        if (optionalHistorial.isPresent()) {
            HistorialClinico historialExistente = optionalHistorial.get();
            historialExistente.setIdCita(historialClinicoActualizado.getIdCita());
            historialExistente.setIdProfesional(historialClinicoActualizado.getIdProfesional());
            historialExistente.setFecha(historialClinicoActualizado.getFecha());
            historialExistente.setDiagnostico(historialClinicoActualizado.getDiagnostico());
            historialExistente.setTratamiento(historialClinicoActualizado.getTratamiento());
            return historialClinicoRepository.save(historialExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        historialClinicoRepository.deleteById(id);
    }
}
