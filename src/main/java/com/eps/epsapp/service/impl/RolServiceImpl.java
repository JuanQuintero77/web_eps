package com.eps.epsapp.service.impl;

import com.eps.epsapp.entity.Rol;
import com.eps.epsapp.repository.RolRepository;
import com.eps.epsapp.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> obtenerPorId(Integer id) {
        return rolRepository.findById(id);
    }

    @Override
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Rol actualizar(Integer id, Rol rolActualizado) {
        Optional<Rol> optionalRol = rolRepository.findById(id);
        if (optionalRol.isPresent()) {
            Rol rolExistente = optionalRol.get();
            rolExistente.setNombreRol(rolActualizado.getNombreRol());
            return rolRepository.save(rolExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }
}
