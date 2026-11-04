package com.archilla.eventia.service;

import com.archilla.eventia.entity.Municipio;
import com.archilla.eventia.entity.Provincia;
import com.archilla.eventia.repository.MunicipioRepository;
import com.archilla.eventia.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de lógica de negocio para la gestión de ubicaciones normalizadas
 * (Provincias y Municipios).
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Optimiza las consultas de solo lectura
public class UbicacionService {

    private final ProvinciaRepository provinciaRepository;
    private final MunicipioRepository municipioRepository;

    /**
     * Obtiene el listado completo de provincias ordenadas alfabéticamente.
     *
     * @return Lista de provincias.
     */
    public List<Provincia> listarProvincias() {
        return provinciaRepository.findAllByOrderByNombreAsc();
    }

    /**
     * Obtiene todos los municipios ordenados alfabéticamente.
     *
     * @return Lista completa de municipios.
     */
    public List<Municipio> listarTodosMunicipios() {
        return municipioRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    /**
     * Obtiene los municipios pertenecientes a una provincia concreta.
     *
     * @param provinciaId Identificador primario de la provincia.
     * @return Lista de municipios de esa provincia.
     */
    public List<Municipio> listarMunicipiosPorProvincia(Long provinciaId) {
        return municipioRepository.findByProvinciaIdOrderByNombreAsc(provinciaId);
    }

    /**
     * Busca un municipio por su ID.
     *
     * @param id Identificador del municipio.
     * @return Optional con el municipio si existe.
     */
    public Optional<Municipio> obtenerMunicipioPorId(Long id) {
        return municipioRepository.findById(id);
    }
}
