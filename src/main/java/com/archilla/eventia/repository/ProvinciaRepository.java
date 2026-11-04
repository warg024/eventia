package com.archilla.eventia.repository;

import com.archilla.eventia.entity.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad Provincia.
 * Proporciona operaciones CRUD y consultas sobre la tabla 'provincias'.
 */
@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    /**
     * Obtiene la lista completa de provincias ordenadas alfabéticamente por su nombre.
     * Útil para llenar el primer desplegable de selección de provincia.
     *
     * @return Lista de provincias ordenadas por nombre.
     */
    List<Provincia> findAllByOrderByNombreAsc();

    /**
     * Busca una provincia a partir de su código oficial de 2 dígitos (ej. "18").
     *
     * @param codigo Código de 2 dígitos de la provincia.
     * @return Un Optional conteniendo la provincia si existe.
     */
    Optional<Provincia> findByCodigo(String codigo);
}