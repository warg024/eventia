package com.archilla.eventia.repository;

import com.archilla.eventia.entity.RecursoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad RecursoStock.
 * Gestiona el acceso a datos del catálogo de material, equipo técnico y personal.
 */
@Repository
public interface RecursoStockRepository extends JpaRepository<RecursoStock, Long> {

    /**
     * Busca un recurso por su código único de referencia.
     */
    Optional<RecursoStock> findByCodigoIgnoreCase(String codigo);

    /**
     * Filtra los recursos de stock por su categoría.
     */
    List<RecursoStock> findByCategoriaOrderByCodigoAsc(String categoria);

    /**
     * Filtra los recursos de stock por su estado operativo.
     */
    List<RecursoStock> findByEstadoOrderByCodigoAsc(String estado);

    /**
     * Búsqueda combinada con filtros opcionales por texto (nombre o código), categoría y estado.
     */
    @Query("SELECT r FROM RecursoStock r WHERE " +
           "(:buscar IS NULL OR :buscar = '' OR LOWER(r.nombre) LIKE LOWER(CONCAT('%', :buscar, '%')) OR LOWER(r.codigo) LIKE LOWER(CONCAT('%', :buscar, '%'))) AND " +
           "(:categoria IS NULL OR :categoria = '' OR r.categoria = :categoria) AND " +
           "(:estado IS NULL OR :estado = '' OR r.estado = :estado) " +
           "ORDER BY r.categoria ASC, r.codigo ASC")
    List<RecursoStock> buscarConFiltros(
            @Param("buscar") String buscar,
            @Param("categoria") String categoria,
            @Param("estado") String estado);
}
