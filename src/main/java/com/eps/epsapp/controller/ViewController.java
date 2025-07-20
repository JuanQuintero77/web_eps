package com.eps.epsapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    
     /**
     * Mapea la raíz del sitio ("/") a la página de login principal.
     */
    @GetMapping("/")
    public String mostrarLoginAfiliado() {
        return "index";
    }

    /**
     * Mapea la URL /login_profesional a su página de login.
     */
    @GetMapping("/login_profesional")
    public String mostrarLoginProfesional() {
        return "login_profesional";
    }

    /**
     * Mapea la URL /login_admin a su página de login.
     */
    @GetMapping("/login_admin")
    public String mostrarLoginAdmin() {
        return "login_admin";
    }

    /**
     * Mapea la URL /registro a la página de registro de afiliados.
     */
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }
}
