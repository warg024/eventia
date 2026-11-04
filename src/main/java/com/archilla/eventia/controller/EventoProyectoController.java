package com.archilla.eventia.controller;

import com.archilla.eventia.entity.AsignacionEvento;
import com.archilla.eventia.entity.Cliente;
import com.archilla.eventia.entity.EventoProyecto;
import com.archilla.eventia.entity.RecursoStock;
import com.archilla.eventia.service.ClienteService;
import com.archilla.eventia.service.EventoProyectoService;
import com.archilla.eventia.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador Spring MVC (Thymeleaf) para la gestión del módulo de Eventos y Proyectos en EVENTIA.
 * Renderiza las vistas HTML de listado (con filtros por estado y cliente), creación y edición con Bootstrap 5.
 */
@Controller
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoProyectoController {

    private final EventoProyectoService eventoService;
    private final ClienteService clienteService;
    private final StockService stockService;

    /**
     * GET /eventos
     * Muestra la vista con el listado de eventos, permitiendo filtrar por estado y/o cliente.
     */
    @GetMapping
    public String listarEventos(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long clienteId,
            Model model) {

        List<EventoProyecto> eventos = eventoService.listarConFiltros(estado, clienteId);
        List<Cliente> clientes = clienteService.listarTodos();

        model.addAttribute("eventos", eventos);
        model.addAttribute("clientes", clientes);
        model.addAttribute("estadoFiltro", estado != null ? estado : "");
        model.addAttribute("clienteIdFiltro", clienteId);
        model.addAttribute("seccionActiva", "eventos");
        return "eventos/lista";
    }

    /**
     * GET /eventos/nuevo
     * Muestra el formulario para registrar un nuevo evento o proyecto.
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        EventoProyecto evento = new EventoProyecto();
        evento.setEstado("PRESUPUESTADO");

        model.addAttribute("evento", evento);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("esEdicion", false);
        model.addAttribute("seccionActiva", "eventos");
        return "eventos/formulario";
    }

    /**
     * GET /eventos/editar/{id}
     * Muestra el formulario para editar un evento existente.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return eventoService.obtenerPorId(id)
                .map(evento -> {
                    model.addAttribute("evento", evento);
                    model.addAttribute("clientes", clienteService.listarTodos());
                    model.addAttribute("esEdicion", true);
                    model.addAttribute("seccionActiva", "eventos");
                    return "eventos/formulario";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("mensajeError", "El evento o proyecto solicitado no existe.");
                    return "redirect:/eventos";
                });
    }

    /**
     * POST /eventos/guardar
     * Procesa el formulario para dar de alta o actualizar un evento/proyecto.
     */
    @PostMapping("/guardar")
    public String guardarEvento(
            @Valid @ModelAttribute("evento") EventoProyecto evento,
            BindingResult result,
            @RequestParam(required = false) Long clienteId,
            Model model,
            RedirectAttributes flash) {

        if (clienteId != null) {
            Cliente cliente = clienteService.obtenerPorId(clienteId).orElse(null);
            evento.setCliente(cliente);
        }

        if (evento.getCliente() == null || evento.getCliente().getId() == null) {
            model.addAttribute("errorMsg", "Debe seleccionar obligatoriamente un cliente contratante.");
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("esEdicion", evento.getId() != null);
            model.addAttribute("seccionActiva", "eventos");
            return "eventos/formulario";
        }

        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("esEdicion", evento.getId() != null);
            model.addAttribute("seccionActiva", "eventos");
            return "eventos/formulario";
        }

        boolean eraEdicion = evento.getId() != null;
        eventoService.guardar(evento);
        flash.addFlashAttribute("mensajeExito",
                eraEdicion ? "Evento / Proyecto actualizado correctamente." : "Nuevo Evento / Proyecto registrado con éxito.");
        return "redirect:/eventos";
    }

    /**
     * GET /eventos/{id}/recursos
     * Muestra el panel de gestión de material, equipo técnico, personal técnico y animadores
     * asignados a un evento específico.
     */
    @GetMapping("/{id}/recursos")
    public String gestionarRecursosEvento(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return eventoService.obtenerPorId(id)
                .map(evento -> {
                    List<AsignacionEvento> asignaciones = stockService.listarAsignacionesPorEvento(id);
                    List<RecursoStock> catalogoDisponible = stockService.listarRecursosOperativos();
                    BigDecimal costeTotalRecursos = stockService.calcularCosteTotalRecursosEvento(id);

                    model.addAttribute("evento", evento);
                    model.addAttribute("asignaciones", asignaciones);
                    model.addAttribute("catalogoDisponible", catalogoDisponible);
                    model.addAttribute("costeTotalRecursos", costeTotalRecursos);
                    model.addAttribute("seccionActiva", "eventos");
                    return "eventos/recursos";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("mensajeError", "El evento solicitado no existe.");
                    return "redirect:/eventos";
                });
    }

    /**
     * POST /eventos/eliminar/{id}
     * Elimina un evento por su ID y redirige al listado.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id, RedirectAttributes flash) {
        if (eventoService.obtenerPorId(id).isPresent()) {
            eventoService.eliminar(id);
            flash.addFlashAttribute("mensajeExito", "Evento eliminado de la programación correctamente.");
        } else {
            flash.addFlashAttribute("mensajeError", "No se encontró el evento a eliminar.");
        }
        return "redirect:/eventos";
    }
}
