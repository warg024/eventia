package com.archilla.eventia.repository;

import com.archilla.eventia.entity.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad Municipio.
 * Proporciona operaciones CRUD y consultas avanzadas sobre la tabla 'municipios'.
 */
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

    /**
     * Filtra y obtiene los municipios pertenecientes a una provincia concreta.
     *
     * Clave para el desplegable anidado: cuando el usuario selecciona la provincia "Granada" (id = 1),
     * este método devuelve únicamente los municipios de Granada ordenados alfabéticamente.
     *
     * @param provinciaId Identificador primario de la provincia.
     * @return Lista de municipios de la provincia seleccionada.
     */
    List<Municipio> findByProvinciaIdOrderByNombreAsc(Long provinciaId);

    /**
     * Busca un municipio por su código postal oficial (ej. "18220").
     *
     * @param codigoPostal Código postal de 5 dígitos.
     * @return Lista de municipios asociados a ese código postal.
     */
    List<Municipio> findByCodigoPostal(String codigoPostal);

    /**
     * Búsqueda por coincidencia parcial en el nombre del municipio (sin distinguir mayúsculas/minúsculas).
     * Ideal para buscadores tipo 'Autocomplete' en Angular o Thymeleaf.
     *
     * @param nombre Texto parcial a buscar (ej. "Albol").
     * @return Lista de municipios coincidentes.
     */
    List<Municipio> findByNombreContainingIgnoreCase(String nombre);
}