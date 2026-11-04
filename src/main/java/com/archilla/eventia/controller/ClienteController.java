package com.archilla.eventia.controller;

import com.archilla.eventia.entity.Cliente;
import com.archilla.eventia.entity.Municipio;
import com.archilla.eventia.service.ClienteService;
import com.archilla.eventia.service.UbicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador Spring MVC (Thymeleaf) para la gestión del módulo de Clientes en EVENTIA.
 * Renderiza las vistas HTML de listado, creación y edición de clientes con Bootstrap 5.
 */
@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final UbicacionService ubicacionService;

    /**
     * GET /clientes
     * Muestra la vista con el listado completo de clientes o filtrado por razón social.
     */
    @GetMapping
    public String listarClientes(@RequestParam(required = false) String buscar, Model model) {
        List<Cliente> clientes;
        if (buscar != null && !buscar.isBlank()) {
            clientes = clienteService.buscarPorRazonSocial(buscar.trim());
        } else {
            clientes = clienteService.listarTodos();
        }

        model.addAttribute("clientes", clientes);
        model.addAttribute("buscar", buscar != null ? buscar : "");
        model.addAttribute("seccionActiva", "clientes");
        return "clientes/lista";
    }

    /**
     * GET /clientes/nuevo
     * Muestra el formulario para registrar un nuevo cliente.
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("esEdicion", false);
        cargarCatalogosUbicacion(model, null);
        model.addAttribute("seccionActiva", "clientes");
        return "clientes/formulario";
    }

    /**
     * GET /clientes/editar/{id}
     * Muestra el formulario con los datos de un cliente existente para su edición.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return clienteService.obtenerPorId(id)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    model.addAttribute("esEdicion", true);
                    Long provinciaId = (cliente.getMunicipio() != null && cliente.getMunicipio().getProvincia() != null)
                            ? cliente.getMunicipio().getProvincia().getId()
                            : null;
                    cargarCatalogosUbicacion(model, provinciaId);
                    model.addAttribute("seccionActiva", "clientes");
                    return "clientes/formulario";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("mensajeError", "El cliente solicitado no existe en la base de datos.");
                    return "redirect:/clientes";
                });
    }

    /**
     * POST /clientes/guardar
     * Procesa el envío del formulario para crear o actualizar un cliente.
     */
    @PostMapping("/guardar")
    public String guardarCliente(
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result,
            @RequestParam(required = false) Long municipioId,
            @RequestParam(required = false) Long provinciaId,
            Model model,
            RedirectAttributes flash) {

        if (municipioId != null) {
            Municipio municipio = ubicacionService.obtenerMunicipioPorId(municipioId).orElse(null);
            cliente.setMunicipio(municipio);
        } else {
            cliente.setMunicipio(null);
        }

        if (result.hasErrors()) {
            model.addAttribute("esEdicion", cliente.getId() != null);
            cargarCatalogosUbicacion(model, provinciaId);
            model.addAttribute("seccionActiva", "clientes");
            return "clientes/formulario";
        }

        try {
            boolean eraEdicion = cliente.getId() != null;
            clienteService.guardar(cliente);
            flash.addFlashAttribute("mensajeExito",
                    eraEdicion ? "Cliente actualizado correctamente." : "Nuevo cliente registrado con éxito.");
            return "redirect:/clientes";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("esEdicion", cliente.getId() != null);
            model.addAttribute("errorMsg", "Error de integridad: Ya existe un cliente registrado con el CIF/NIF indicado.");
            cargarCatalogosUbicacion(model, provinciaId);
            model.addAttribute("seccionActiva", "clientes");
            return "clientes/formulario";
        }
    }

    /**
     * POST /clientes/eliminar/{id}
     * Elimina un cliente por su ID y redirige al listado.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes flash) {
        if (clienteService.obtenerPorId(id).isEmpty()) {
            flash.addFlashAttribute("mensajeError", "No se encontró el cliente a eliminar.");
            return "redirect:/clientes";
        }

        try {
            clienteService.eliminar(id);
            flash.addFlashAttribute("mensajeExito", "Cliente eliminado correctamente.");
        } catch (DataIntegrityViolationException e) {
            flash.addFlashAttribute("mensajeError",
                    "No se puede eliminar el cliente porque tiene eventos o proyectos asociados.");
        }
        return "redirect:/clientes";
    }

    private void cargarCatalogosUbicacion(Model model, Long provinciaSeleccionadaId) {
        model.addAttribute("provincias", ubicacionService.listarProvincias());
        model.addAttribute("municipios", ubicacionService.listarTodosMunicipios());
        model.addAttribute("provinciaSeleccionadaId", provinciaSeleccionadaId);
    }
}
