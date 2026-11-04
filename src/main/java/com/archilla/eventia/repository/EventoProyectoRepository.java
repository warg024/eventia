package com.archilla.eventia.repository;

import com.archilla.eventia.entity.EventoProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad EventoProyecto.
 * Gestiona el acceso a datos para los eventos y proyectos de EVENTIA.
 */
@Repository
public interface EventoProyectoRepository extends JpaRepository<EventoProyecto, Long> {

    /**
     * Obtiene el historial completo de eventos contratados por un cliente concreto.
     */
    List<EventoProyecto> findByClienteId(Long clienteId);

    /**
     * Filtra eventos según su estado.
     */
    List<EventoProyecto> findByEstado(String estado);

    /**
     * Filtra eventos combinando estado y cliente contratante.
     */
    List<EventoProyecto> findByEstadoAndClienteId(String estado, Long clienteId);

    /**
     * Búsqueda de eventos programados dentro de un rango de fechas de inicio.
     */
    List<EventoProyecto> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
