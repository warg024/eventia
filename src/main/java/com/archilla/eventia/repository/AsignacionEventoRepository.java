package com.archilla.eventia.repository;

import com.archilla.eventia.entity.AsignacionEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad AsignacionEvento.
 * Gestiona las asignaciones de material, equipo técnico y personal a los eventos.
 */
@Repository
public interface AsignacionEventoRepository extends JpaRepository<AsignacionEvento, Long> {

    /**
     * Obtiene todas las asignaciones asociadas a un evento específico.
     */
    List<AsignacionEvento> findByEventoIdOrderByIdDesc(Long eventoId);

    /**
     * Obtiene todas las asignaciones históricas y activas de un recurso de stock.
     */
    List<AsignacionEvento> findByRecursoIdOrderByIdDesc(Long recursoId);
}
