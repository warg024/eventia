package com.archilla.eventia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador MVC principal para el enrutamiento raíz del sistema EVENTIA.
 * Redirige el acceso principal a la cartera de clientes.
 */
@Controller
public class UbicacionController {

    /**
     * GET /
     * Redirige la ruta raíz hacia el módulo principal de Clientes.
     */
    @GetMapping("/")
    public String inicio() {
        return "redirect:/clientes";
    }
}
