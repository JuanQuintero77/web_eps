package com.eps.epsapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profesional") // Todas las rutas aquí comenzarán con /profesional
public class ProfesionalViewController {

    @GetMapping("/dashboard")
    public String mostrarDashboard() {
        return "profesional/dashboard";
    }

    @GetMapping("/paciente")
    public String mostrarAtenderPaciente() {
        // En un futuro, este método recibiría el ID de la cita
        return "profesional/paciente";
    }

    @GetMapping("/perfil_profesional")
    public String mostrarPerfil() {
        return "profesional/perfil_profesional";
    }
}
