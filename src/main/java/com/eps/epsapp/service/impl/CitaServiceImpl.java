package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Cita;
import com.eps.epsapp.repository.CitaRepository;
import com.eps.epsapp.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Cita> listarTodos() {
        return citaRepository.findAll();
    }

    @Override
    public Optional<Cita> obtenerPorId(Integer id) {
        return citaRepository.findById(id);
    }

    @Override
    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public Cita actualizar(Integer id, Cita citaActualizada) {
        return citaRepository.findById(id).map(cita -> {
            cita.setAfiliado(citaActualizada.getAfiliado());
            cita.setProfesional(citaActualizada.getProfesional());
            cita.setFecha(citaActualizada.getFecha());
            cita.setHora(citaActualizada.getHora());
            cita.setMotivo(citaActualizada.getMotivo());
            cita.setEstadoCita(citaActualizada.getEstadoCita());
            return citaRepository.save(cita);
        }).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<Cita> obtenerCitasPorProfesional(Integer idProfesional) {
        return citaRepository.findByProfesional_IdProfesional(idProfesional);
    }
}
