package com.archilla.eventia.service;

import com.archilla.eventia.entity.AsignacionEvento;
import com.archilla.eventia.entity.EventoProyecto;
import com.archilla.eventia.entity.RecursoStock;
import com.archilla.eventia.repository.AsignacionEventoRepository;
import com.archilla.eventia.repository.EventoProyectoRepository;
import com.archilla.eventia.repository.RecursoStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de lógica de negocio para el módulo de Stock, Equipos y Personal de EVENTIA.
 * Gestiona tanto el catálogo de recursos como su asignación y disponibilidad en eventos.
 */
@Service
@RequiredArgsConstructor
public class StockService {

    private final RecursoStockRepository recursoRepository;
    private final AsignacionEventoRepository asignacionRepository;
    private final EventoProyectoRepository eventoRepository;

    // =========================================================================
    // GESTIÓN DEL CATÁLOGO DE RECURSOS DE STOCK
    // =========================================================================

    @Transactional(readOnly = true)
    public List<RecursoStock> listarTodos() {
        return recursoRepository.buscarConFiltros(null, null, null);
    }

    @Transactional(readOnly = true)
    public List<RecursoStock> listarConFiltros(String buscar, String categoria, String estado) {
        String filtroBuscar = (buscar != null && !buscar.isBlank()) ? buscar.trim() : null;
        String filtroCategoria = (categoria != null && !categoria.isBlank()) ? categoria.trim() : null;
        String filtroEstado = (estado != null && !estado.isBlank()) ? estado.trim() : null;
        return recursoRepository.buscarConFiltros(filtroBuscar, filtroCategoria, filtroEstado);
    }

    @Transactional(readOnly = true)
    public List<RecursoStock> listarRecursosOperativos() {
        return recursoRepository.findByEstadoOrderByCodigoAsc("DISPONIBLE");
    }

    @Transactional(readOnly = true)
    public Optional<RecursoStock> obtenerRecursoPorId(Long id) {
        return recursoRepository.findById(id);
    }

    @Transactional
    public RecursoStock guardarRecurso(RecursoStock recurso) {
        if (recurso.getCodigo() != null) {
            recurso.setCodigo(recurso.getCodigo().trim().toUpperCase());
        }
        return recursoRepository.save(recurso);
    }

    @Transactional
    public void eliminarRecurso(Long id) {
        recursoRepository.deleteById(id);
    }

    // =========================================================================
    // GESTIÓN DE ASIGNACIONES A EVENTOS Y CONTROL DE STOCK
    // =========================================================================

    @Transactional(readOnly = true)
    public List<AsignacionEvento> listarAsignacionesPorEvento(Long eventoId) {
        return asignacionRepository.findByEventoIdOrderByIdDesc(eventoId);
    }

    @Transactional(readOnly = true)
    public List<AsignacionEvento> listarAsignacionesPorRecurso(Long recursoId) {
        return asignacionRepository.findByRecursoIdOrderByIdDesc(recursoId);
    }

    @Transactional(readOnly = true)
    public Optional<AsignacionEvento> obtenerAsignacionPorId(Long id) {
        return asignacionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularCosteTotalRecursosEvento(Long eventoId) {
        return listarAsignacionesPorEvento(eventoId).stream()
                .map(AsignacionEvento::getCosteEstimado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Registra la asignación de un recurso (material, equipo o personal) a un evento,
     * validando que existan unidades disponibles suficientes en el stock.
     */
    @Transactional
    public AsignacionEvento asignarRecursoAEvento(
            Long eventoId,
            Long recursoId,
            Integer cantidad,
            String estadoAsignacion,
            String responsable,
            String observaciones) {

        EventoProyecto evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("El evento seleccionado no existe."));

        RecursoStock recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new IllegalArgumentException("El recurso de stock seleccionado no existe."));

        if (cantidad == null || cantidad < 1) {
            throw new IllegalArgumentException("La cantidad a asignar debe ser de al menos 1 unidad.");
        }

        String estadoFinal = (estadoAsignacion != null && !estadoAsignacion.isBlank())
                ? estadoAsignacion.trim()
                : "RESERVADO";

        // Si la asignación consume stock activo, validamos disponibilidad
        boolean consumeStock = !"DEVUELTO".equalsIgnoreCase(estadoFinal)
                && !"FINALIZADO".equalsIgnoreCase(evento.getEstado());

        if (consumeStock) {
            if (!"DISPONIBLE".equalsIgnoreCase(recurso.getEstado())) {
                throw new IllegalArgumentException(
                        "El recurso '" + recurso.getNombre() + "' no está operativo actualmente (" + recurso.getEstado() + ").");
            }
            int disponibles = recurso.getCantidadDisponible();
            if (cantidad > disponibles) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para '" + recurso.getNombre() + "'. Unidades solicitadas: "
                                + cantidad + ", disponibles actualmente: " + disponibles + ".");
            }
        }

        AsignacionEvento asignacion = new AsignacionEvento();
        asignacion.setEvento(evento);
        asignacion.setRecurso(recurso);
        asignacion.setCantidad(cantidad);
        asignacion.setFechaAsignacion(LocalDate.now());
        asignacion.setEstadoAsignacion(estadoFinal);
        asignacion.setResponsable(responsable != null && !responsable.isBlank() ? responsable.trim() : null);
        asignacion.setObservaciones(observaciones != null && !observaciones.isBlank() ? observaciones.trim() : null);

        return asignacionRepository.save(asignacion);
    }

    /**
     * Actualiza el estado de una asignación existente (ej. RESERVADO -> EN_USO -> DEVUELTO).
     */
    @Transactional
    public void cambiarEstadoAsignacion(Long asignacionId, String nuevoEstado) {
        AsignacionEvento asignacion = asignacionRepository.findById(asignacionId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la asignación indicada."));

        RecursoStock recurso = asignacion.getRecurso();
        boolean estabaDevuelto = "DEVUELTO".equalsIgnoreCase(asignacion.getEstadoAsignacion());
        boolean pasaAConsumir = !"DEVUELTO".equalsIgnoreCase(nuevoEstado)
                && (asignacion.getEvento() == null || !"FINALIZADO".equalsIgnoreCase(asignacion.getEvento().getEstado()));

        if (estabaDevuelto && pasaAConsumir) {
            int disponibles = recurso.getCantidadDisponible();
            if (asignacion.getCantidad() > disponibles) {
                throw new IllegalArgumentException(
                        "No hay suficientes unidades libres de '" + recurso.getNombre()
                                + "' para reactivar esta asignación (disponibles: " + disponibles + ").");
            }
        }

        asignacion.setEstadoAsignacion(nuevoEstado);
        asignacionRepository.save(asignacion);
    }

    /**
     * Elimina una asignación liberando inmediatamente el stock comprometido.
     */
    @Transactional
    public void eliminarAsignacion(Long asignacionId) {
        asignacionRepository.deleteById(asignacionId);
    }
}
