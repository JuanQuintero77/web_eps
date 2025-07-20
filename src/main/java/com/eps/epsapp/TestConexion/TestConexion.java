package com.eps.epsapp.TestConexion;

import com.eps.epsapp.entity.Rol;
import com.eps.epsapp.repository.RolRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestConexion {

    @Autowired
    private RolRepository rolRepository;

    @PostConstruct
    public void mostrarRoles() {
        List<Rol> roles = rolRepository.findAll();

        System.out.println("🔎 Lista de roles encontrados en la base de datos:");
        for (Rol rol : roles) {
            System.out.println("🟢 ID: " + rol.getIdRol() + " | Nombre: " + rol.getNombreRol());
        }
    }
}