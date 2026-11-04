package com.archilla.eventia.repository;

import com.archilla.eventia.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad Cliente.
 * Proporciona operaciones CRUD y búsquedas avanzadas sobre la tabla 'clientes'.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca un cliente a partir de su CIF/NIF fiscal único.
     *
     * @param cif CIF/NIF del cliente.
     * @return Un Optional conteniendo el cliente si existe.
     */
    Optional<Cliente> findByCif(String cif);

    /**
     * Búsqueda por coincidencia parcial en la Razón Social (sin distinguir mayúsculas/minúsculas).
     * Ideal para buscadores y filtros de clientes.
     *
     * @param razonSocial Texto a buscar dentro de la razón social.
     * @return Lista de clientes coincidentes.
     */
    List<Cliente> findByRazonSocialContainingIgnoreCase(String razonSocial);

    /**
     * Obtiene todos los clientes asociados a un municipio específico.
     *
     * @param municipioId Identificador primario del municipio.
     * @return Lista de clientes ubicados en dicho municipio.
     */
    List<Cliente> findByMunicipioId(Long municipioId);
}
