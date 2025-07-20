package com.eps.epsapp.controller;

    
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // IMPORTANTE: Todas las rutas en esta clase empezarán con /admin
public class AdminViewController {

     @GetMapping("/dashboard")
    public String mostrarAdminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/gestionar_afiliados")
    public String mostrarGestionarAfiliados() {
        return "admin/gestionar_afiliados";
    }

    @GetMapping("/gestionar_profesionales")
    public String mostrarGestionarProfesionales() {
        return "admin/gestionar_profesionales";
    }

    @GetMapping("/gestionar_administradores")
    public String mostrarGestionarAdministradores() {
        return "admin/gestionar_administradores";
    }

    @GetMapping("/gestionar_centros")
    public String mostrarGestionarCentros() {
        return "admin/gestionar_centros";
    }

    @GetMapping("/gestionar_especialidades")
    public String mostrarGestionarEspecialidades() {
        return "admin/gestionar_especialidades";
    }

    @GetMapping("/gestionar_planes")
    public String mostrarGestionarPlanes() {
        return "admin/gestionar_planes";
    }
}
