package com.archilla.eventia.controller;

import com.archilla.eventia.entity.Cliente;
import com.archilla.eventia.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador RESTful para la gestión del módulo de Clientes en EVENTIA.
 * Expone endpoints JSON para operaciones CRUD consumibles por Angular / Apps móviles.
 *
 * Ruta base: /api/v1/clientes
 */
@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * GET /api/v1/clientes
     * Obtiene la lista completa de clientes.
     * Admite filtrado opcional por razón social mediante un query param: /api/v1/clientes?buscar=Ayuntamiento
     *
     * @param buscar Texto opcional para filtrar por razón social.
     * @return Lista de clientes en formato JSON (HTTP 200 OK).
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(@RequestParam(required = false) String buscar) {
        if (buscar != null && !buscar.isBlank()) {
            return ResponseEntity.ok(clienteService.buscarPorRazonSocial(buscar));
        }
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    /**
     * GET /api/v1/clientes/{id}
     * Obtiene los detalles de un cliente específico a partir de su ID.
     *
     * @param id Identificador primario del cliente.
     * @return Cliente encontrado (HTTP 200 OK) o HTTP 404 Not Found si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/clientes
     * Crea y persiste un nuevo cliente en la base de datos.
     *
     * @param cliente Objeto cliente enviado en el cuerpo de la petición (JSON).
     * @return Cliente guardado con su ID asignado y estado HTTP 201 Created.
     */
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        Cliente clienteGuardado = clienteService.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
    }

    /**
     * PUT /api/v1/clientes/{id}
     * Actualiza los datos de un cliente existente.
     *
     * @param id Identificador del cliente a modificar.
     * @param clienteDetalles Datos actualizados del cliente.
     * @return Cliente actualizado (HTTP 200 OK) o HTTP 404 Not Found si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody Cliente clienteDetalles) {

        return clienteService.obtenerPorId(id)
                .map(clienteExistente -> {
                    clienteExistente.setRazonSocial(clienteDetalles.getRazonSocial());
                    clienteExistente.setCif(clienteDetalles.getCif());
                    clienteExistente.setPersonaContacto(clienteDetalles.getPersonaContacto());
                    clienteExistente.setEmail(clienteDetalles.getEmail());
                    clienteExistente.setTelefono(clienteDetalles.getTelefono());
                    clienteExistente.setDireccionCalle(clienteDetalles.getDireccionCalle());
                    clienteExistente.setMunicipio(clienteDetalles.getMunicipio());

                    Cliente clienteActualizado = clienteService.guardar(clienteExistente);
                    return ResponseEntity.ok(clienteActualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/v1/clientes/{id}
     * Elimina un cliente de la base de datos por su ID.
     *
     * @param id Identificador del cliente a eliminar.
     * @return HTTP 204 No Content tras la eliminación exitosa, o 404 Not Found si no existía.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (clienteService.obtenerPorId(id).isPresent()) {
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
