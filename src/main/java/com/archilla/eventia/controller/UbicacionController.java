package com.archilla.eventia.controller;

import com.archilla.eventia.entity.Municipio;
import com.archilla.eventia.entity.Provincia;
import com.archilla.eventia.service.UbicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador RESTful para la gestión de Ubicaciones (Provincias y Municipios) en EVENTIA.
 * Proporciona los datos del catálogo maestro para la validación de direcciones.
 *
 * Ruta base: /api/v1/ubicaciones
 */
@RestController
@RequestMapping("/api/v1/ubicaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    /**
     * GET /api/v1/ubicaciones/provincias
     * Obtiene el catálogo completo de provincias ordenadas alfabéticamente.
     *
     * @return Lista de provincias en JSON (HTTP 200 OK).
     */
    @GetMapping("/provincias")
    public ResponseEntity<List<Provincia>> listarProvincias() {
        return ResponseEntity.ok(ubicacionService.listarProvincias());
    }

    /**
     * GET /api/v1/ubicaciones/provincias/{provinciaId}/municipios
     * Obtiene los municipios pertenecientes a una provincia específica.
     * Clave para cargar el desplegable anidado en Angular / HTML.
     *
     * @param provinciaId Identificador primario de la provincia.
     * @return Lista de municipios de esa provincia (HTTP 200 OK).
     */
    @GetMapping("/provincias/{provinciaId}/municipios")
    public ResponseEntity<List<Municipio>> listarMunicipiosPorProvincia(@PathVariable Long provinciaId) {
        return ResponseEntity.ok(ubicacionService.listarMunicipiosPorProvincia(provinciaId));
    }

    /**
     * GET /api/v1/ubicaciones/municipios/{id}
     * Obtiene la información detallada de un municipio por su ID.
     *
     * @param id Identificador del municipio.
     * @return Municipio encontrado (HTTP 200 OK) o 404 Not Found.
     */
    @GetMapping("/municipios/{id}")
    public ResponseEntity<Municipio> obtenerMunicipioPorId(@PathVariable Long id) {
        return ubicacionService.obtenerMunicipioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
