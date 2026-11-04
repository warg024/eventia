package com.archilla.eventia.service;

import com.archilla.eventia.entity.EventoProyecto;
import com.archilla.eventia.repository.EventoProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Servicio de lógica de negocio para el módulo de Eventos y Proyectos.
@Service
@RequiredArgsConstructor
public class EventoProyectoService {

    private final EventoProyectoRepository eventoRepository;

    // Devuelve la lista completa de eventos registrados.
    @Transactional(readOnly = true)
    public List<EventoProyecto> listarTodos() {
        return eventoRepository.findAll();
    }

    // Filtra eventos combinando estado y/o cliente.
    @Transactional(readOnly = true)
    public List<EventoProyecto> listarConFiltros(String estado, Long clienteId) {
        boolean tieneEstado = estado != null && !estado.isBlank();
        boolean tieneCliente = clienteId != null;

        if (tieneEstado && tieneCliente) {
            return eventoRepository.findByEstadoAndClienteId(estado.trim(), clienteId);
        } else if (tieneEstado) {
            return eventoRepository.findByEstado(estado.trim());
        } else if (tieneCliente) {
            return eventoRepository.findByClienteId(clienteId);
        }
        return eventoRepository.findAll();
    }

    // Obtiene un evento por su ID.
    @Transactional(readOnly = true)
    public Optional<EventoProyecto> obtenerPorId(Long id) {
        return eventoRepository.findById(id);
    }

    // Filtra los eventos contratados por un cliente específico.
    @Transactional(readOnly = true)
    public List<EventoProyecto> listarPorCliente(Long clienteId) {
        return eventoRepository.findByClienteId(clienteId);
    }

    // Filtra eventos por su estado actual (ej: "CONFIRMADO", "PRESUPUESTADO").
    @Transactional(readOnly = true)
    public List<EventoProyecto> listarPorEstado(String estado) {
        return eventoRepository.findByEstado(estado);
    }

    // Obtiene eventos programados entre dos fechas.
    @Transactional(readOnly = true)
    public List<EventoProyecto> listarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return eventoRepository.findByFechaInicioBetween(inicio, fin);
    }

    // Guarda o actualiza un evento/proyecto.
    @Transactional
    public EventoProyecto guardar(EventoProyecto evento) {
        return eventoRepository.save(evento);
    }

    // Elimina un evento por su ID.
    @Transactional
    public void eliminar(Long id) {
        eventoRepository.deleteById(id);
    }
}
