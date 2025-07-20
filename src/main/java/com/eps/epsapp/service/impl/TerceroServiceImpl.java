package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Tercero;
import com.eps.epsapp.repository.TerceroRepository;
import com.eps.epsapp.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerceroServiceImpl implements TerceroService {

    @Autowired
    private TerceroRepository terceroRepository;

    @Override
    public List<Tercero> listarTodos() {
        return terceroRepository.findAll();
    }

    @Override
    public Optional<Tercero> obtenerPorId(Integer id) {
        return terceroRepository.findById(id);
    }

    @Override
    public Tercero guardar(Tercero tercero) {
        return terceroRepository.save(tercero);
    }

    @Override
    public Tercero actualizar(Integer id, Tercero terceroActualizado) {
        Optional<Tercero> optional = terceroRepository.findById(id);
        if (optional.isPresent()) {
            Tercero existente = optional.get();
            existente.setTipoDocumento(terceroActualizado.getTipoDocumento());
            existente.setNumeroDocumento(terceroActualizado.getNumeroDocumento());
            existente.setNombre(terceroActualizado.getNombre());
            existente.setApellido(terceroActualizado.getApellido());
            existente.setTelefono(terceroActualizado.getTelefono());
            existente.setCorreo(terceroActualizado.getCorreo());
            return terceroRepository.save(existente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        terceroRepository.deleteById(id);
    }
}
