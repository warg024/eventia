package com.archilla.eventia.controller;

import com.archilla.eventia.entity.AsignacionEvento;
import com.archilla.eventia.entity.EventoProyecto;
import com.archilla.eventia.entity.RecursoStock;
import com.archilla.eventia.service.EventoProyectoService;
import com.archilla.eventia.service.StockService;
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
 * Controlador Spring MVC (Thymeleaf) para la gestión del módulo de Stock y Recursos en EVENTIA.
 * Permite organizar, dar de alta, editar y asignar material, equipo técnico,
 * personal técnico y animadores a los eventos.
 */
@Controller
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final EventoProyectoService eventoService;

    /**
     * GET /stock
     * Muestra el catálogo de stock y recursos con filtros por texto, categoría y estado,
     * además de indicadores resumen de inventario y personal.
     */
    @GetMapping
    public String listarStock(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String estado,
            Model model) {

        List<RecursoStock> recursos = stockService.listarConFiltros(buscar, categoria, estado);
        List<RecursoStock> todos = stockService.listarTodos();

        int totalReferencias = todos.size();
        int totalUnidadesDisponibles = todos.stream().mapToInt(RecursoStock::getCantidadDisponible).sum();
        int totalUnidadesAsignadas = todos.stream().mapToInt(RecursoStock::getCantidadAsignada).sum();
        long totalPerfilesPersonal = todos.stream().filter(RecursoStock::isPersonal).count();

        model.addAttribute("recursos", recursos);
        model.addAttribute("buscarFiltro", buscar != null ? buscar : "");
        model.addAttribute("categoriaFiltro", categoria != null ? categoria : "");
        model.addAttribute("estadoFiltro", estado != null ? estado : "");
        model.addAttribute("totalReferencias", totalReferencias);
        model.addAttribute("totalUnidadesDisponibles", totalUnidadesDisponibles);
        model.addAttribute("totalUnidadesAsignadas", totalUnidadesAsignadas);
        model.addAttribute("totalPerfilesPersonal", totalPerfilesPersonal);
        model.addAttribute("seccionActiva", "stock");
        return "stock/lista";
    }

    /**
     * GET /stock/nuevo
     * Muestra el formulario para registrar un nuevo material, equipo técnico, técnico o animador.
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        RecursoStock recurso = new RecursoStock();
        recurso.setEstado("DISPONIBLE");
        recurso.setCantidadTotal(1);

        model.addAttribute("recurso", recurso);
        model.addAttribute("esEdicion", false);
        model.addAttribute("seccionActiva", "stock");
        return "stock/formulario";
    }

    /**
     * GET /stock/editar/{id}
     * Muestra el formulario para editar un recurso existente del catálogo.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return stockService.obtenerRecursoPorId(id)
                .map(recurso -> {
                    model.addAttribute("recurso", recurso);
                    model.addAttribute("esEdicion", true);
                    model.addAttribute("seccionActiva", "stock");
                    return "stock/formulario";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("mensajeError", "El recurso de stock solicitado no existe.");
                    return "redirect:/stock";
                });
    }

    /**
     * POST /stock/guardar
     * Procesa el formulario de alta o actualización de un recurso de stock.
     */
    @PostMapping("/guardar")
    public String guardarRecurso(
            @Valid @ModelAttribute("recurso") RecursoStock recurso,
            BindingResult result,
            Model model,
            RedirectAttributes flash) {

        boolean eraEdicion = recurso.getId() != null;

        if (result.hasErrors()) {
            model.addAttribute("esEdicion", eraEdicion);
            model.addAttribute("seccionActiva", "stock");
            return "stock/formulario";
        }

        // Si es edición, conservamos sus asignaciones para validar que la cantidad total no sea menor a la asignada
        if (eraEdicion) {
            RecursoStock actual = stockService.obtenerRecursoPorId(recurso.getId()).orElse(null);
            if (actual != null) {
                int asignadasActivas = actual.getCantidadAsignada();
                if (recurso.getCantidadTotal() < asignadasActivas) {
                    model.addAttribute("esEdicion", true);
                    model.addAttribute("errorMsg",
                            "La cantidad total (" + recurso.getCantidadTotal() + ") no puede ser inferior a las unidades actualmente asignadas a eventos activos (" + asignadasActivas + ").");
                    model.addAttribute("seccionActiva", "stock");
                    return "stock/formulario";
                }
                recurso.setAsignaciones(actual.getAsignaciones());
            }
        }

        try {
            stockService.guardarRecurso(recurso);
            flash.addFlashAttribute("mensajeExito",
                    eraEdicion ? "Recurso de stock actualizado correctamente." : "Nuevo recurso añadido al catálogo de stock con éxito.");
            return "redirect:/stock";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("esEdicion", eraEdicion);
            model.addAttribute("errorMsg", "Error de integridad: Ya existe un recurso registrado con el código '" + recurso.getCodigo() + "'.");
            model.addAttribute("seccionActiva", "stock");
            return "stock/formulario";
        }
    }

    /**
     * POST /stock/eliminar/{id}
     * Elimina un recurso del catálogo si no tiene asignaciones activas.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminarRecurso(@PathVariable Long id, RedirectAttributes flash) {
        return stockService.obtenerRecursoPorId(id)
                .map(recurso -> {
                    if (!recurso.getAsignaciones().isEmpty()) {
                        flash.addFlashAttribute("mensajeError",
                                "No se puede eliminar '" + recurso.getNombre() + "' porque tiene asignaciones registradas en eventos. Libere o elimine primero sus asignaciones.");
                        return "redirect:/stock";
                    }
                    stockService.eliminarRecurso(id);
                    flash.addFlashAttribute("mensajeExito", "Recurso eliminado del catálogo de stock correctamente.");
                    return "redirect:/stock";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("mensajeError", "No se encontró el recurso a eliminar.");
                    return "redirect:/stock";
                });
    }

    /**
     * GET /stock/detalle/{id}
     * Muestra la ficha detallada de un recurso de stock, su disponibilidad y el panel
     * para asignarlo directamente a cualquier evento activo.
     */
    @GetMapping("/detalle/{id}")
    public String detalleRecurso(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return stockService.obtenerRecursoPorId(id)
                .map(recurso -> {
                    List<AsignacionEvento> asignaciones = stockService.listarAsignacionesPorRecurso(id);
                    List<EventoProyecto> eventosActivos = eventoService.listarTodos().stream()
                            .filter(e -> !"FINALIZADO".equalsIgnoreCase(e.getEstado()))
                            .toList();

                    model.addAttribute("recurso", recurso);
                    model.addAttribute("asignaciones", asignaciones);
                    model.addAttribute("eventosActivos", eventosActivos);
                    model.addAttribute("seccionActiva", "stock");
                    return "stock/detalle";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("mensajeError", "El recurso solicitado no existe.");
                    return "redirect:/stock";
                });
    }

    /**
     * POST /stock/asignar
     * Asigna un recurso de stock a un evento (invocable tanto desde la ficha del Recurso como desde la gestión del Evento).
     */
    @PostMapping("/asignar")
    public String asignarRecurso(
            @RequestParam Long eventoId,
            @RequestParam Long recursoId,
            @RequestParam Integer cantidad,
            @RequestParam(defaultValue = "RESERVADO") String estadoAsignacion,
            @RequestParam(required = false) String responsable,
            @RequestParam(required = false) String observaciones,
            @RequestParam(defaultValue = "evento") String origen,
            RedirectAttributes flash) {

        try {
            AsignacionEvento asignacion = stockService.asignarRecursoAEvento(
                    eventoId, recursoId, cantidad, estadoAsignacion, responsable, observaciones);
            flash.addFlashAttribute("mensajeExito",
                    "Asignación registrada: " + asignacion.getCantidad() + " ud(s). de '"
                            + asignacion.getRecurso().getNombre() + "' para el evento '"
                            + asignacion.getEvento().getNombreEvento() + "'.");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("mensajeError", e.getMessage());
        }

        if ("stock".equalsIgnoreCase(origen)) {
            return "redirect:/stock/detalle/" + recursoId;
        }
        return "redirect:/eventos/" + eventoId + "/recursos";
    }

    /**
     * POST /stock/asignaciones/{id}/estado
     * Cambia el estado de una asignación (RESERVADO, EN_USO, DEVUELTO).
     */
    @PostMapping("/asignaciones/{id}/estado")
    public String cambiarEstadoAsignacion(
            @PathVariable Long id,
            @RequestParam String nuevoEstado,
            @RequestParam(defaultValue = "evento") String origen,
            RedirectAttributes flash) {

        AsignacionEvento asignacion = stockService.obtenerAsignacionPorId(id).orElse(null);
        if (asignacion == null) {
            flash.addFlashAttribute("mensajeError", "No se encontró la asignación solicitada.");
            return "redirect:/stock";
        }

        Long eventoId = asignacion.getEvento().getId();
        Long recursoId = asignacion.getRecurso().getId();

        try {
            stockService.cambiarEstadoAsignacion(id, nuevoEstado);
            flash.addFlashAttribute("mensajeExito",
                    "Estado de la asignación actualizado a " + nuevoEstado + ".");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("mensajeError", e.getMessage());
        }

        if ("stock".equalsIgnoreCase(origen)) {
            return "redirect:/stock/detalle/" + recursoId;
        }
        return "redirect:/eventos/" + eventoId + "/recursos";
    }

    /**
     * POST /stock/asignaciones/{id}/eliminar
     * Elimina una asignación y libera las unidades ocupadas en el stock.
     */
    @PostMapping("/asignaciones/{id}/eliminar")
    public String eliminarAsignacion(
            @PathVariable Long id,
            @RequestParam(defaultValue = "evento") String origen,
            RedirectAttributes flash) {

        AsignacionEvento asignacion = stockService.obtenerAsignacionPorId(id).orElse(null);
        if (asignacion == null) {
            flash.addFlashAttribute("mensajeError", "No se encontró la asignación a eliminar.");
            return "redirect:/stock";
        }

        Long eventoId = asignacion.getEvento().getId();
        Long recursoId = asignacion.getRecurso().getId();

        stockService.eliminarAsignacion(id);
        flash.addFlashAttribute("mensajeExito", "Asignación eliminada y stock liberado correctamente.");

        if ("stock".equalsIgnoreCase(origen)) {
            return "redirect:/stock/detalle/" + recursoId;
        }
        return "redirect:/eventos/" + eventoId + "/recursos";
    }
}
