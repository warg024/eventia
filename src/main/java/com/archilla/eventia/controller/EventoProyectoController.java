package com.archilla.eventia.controller;

import com.archilla.eventia.entity.EventoProyecto;
import com.archilla.eventia.service.EventoProyectoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador RESTful para la gestión del módulo de Eventos y Proyectos en EVENTIA.
 * Expone endpoints JSON para operaciones CRUD y filtrado por estado, cliente o fechas.
 *
 * Ruta base: /api/v1/eventos
 */
@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EventoProyectoController {

    private final EventoProyectoService eventoService;

    /**
     * GET /api/v1/eventos
     * Obtiene la lista completa de eventos o filtrada por estado y/o cliente.
     */
    @GetMapping
    public ResponseEntity<List<EventoProyecto>> listarEventos(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long clienteId) {

        return ResponseEntity.ok(eventoService.listarConFiltros(estado, clienteId));
    }

    /**
     * GET /api/v1/eventos/rango-fechas?inicio=2027-01-01&fin=2027-01-31
     * Obtiene eventos programados entre dos fechas.
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<EventoProyecto>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        return ResponseEntity.ok(eventoService.listarPorRangoFechas(inicio, fin));
    }

    /**
     * GET /api/v1/eventos/{id}
     * Obtiene los detalles de un evento por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventoProyecto> obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/eventos
     * Registra un nuevo evento/proyecto en la base de datos.
     */
    @PostMapping
    public ResponseEntity<EventoProyecto> crearEvento(@Valid @RequestBody EventoProyecto evento) {
        EventoProyecto nuevoEvento = eventoService.guardar(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEvento);
    }

    /**
     * PUT /api/v1/eventos/{id}
     * Actualiza los datos de un evento existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventoProyecto> actualizarEvento(
            @PathVariable Long id,
            @Valid @RequestBody EventoProyecto detalles) {

        return eventoService.obtenerPorId(id)
                .map(existente -> {
                    existente.setNombreEvento(detalles.getNombreEvento());
                    existente.setFechaInicio(detalles.getFechaInicio());
                    existente.setFechaFin(detalles.getFechaFin());
                    existente.setPresupuesto(detalles.getPresupuesto());
                    existente.setEstado(detalles.getEstado());
                    existente.setUbicacion(detalles.getUbicacion());
                    existente.setCliente(detalles.getCliente());

                    EventoProyecto actualizado = eventoService.guardar(existente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/v1/eventos/{id}
     * Elimina un evento por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        if (eventoService.obtenerPorId(id).isPresent()) {
            eventoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
