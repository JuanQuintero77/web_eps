package com.eps.epsapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/afiliado") // Todas las rutas aquí comenzarán con /afiliado
public class AfiliadoViewController {

     @GetMapping("/dashboard")
    public String mostrarDashboard() {
        return "afiliado/dashboard";
    }

    @GetMapping("/citas")
    public String mostrarCitas() {
        return "afiliado/citas";
    }

    @GetMapping("/historial")
    public String mostrarHistorial() {
        return "afiliado/historial";
    }
    
    @GetMapping("/facturas")
    public String mostrarFacturas() {
        return "afiliado/facturas";
    }

    @GetMapping("/perfil")
    public String mostrarPerfil() {
        return "afiliado/perfil";
    }
}
