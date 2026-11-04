package com.archilla.eventia.service;

import com.archilla.eventia.entity.Cliente;
import com.archilla.eventia.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de lógica de negocio para el módulo de Clientes.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Obtiene todos los clientes registrados.
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su ID único.
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Busca un cliente por su CIF/NIF fiscal.
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> obtenerPorCif(String cif) {
        return clienteRepository.findByCif(cif);
    }

    /**
     * Busca clientes cuya razón social contenga el texto indicado.
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorRazonSocial(String texto) {
        return clienteRepository.findByRazonSocialContainingIgnoreCase(texto);
    }

    /**
     * Guarda o actualiza un cliente en la base de datos.
     *
     * @param cliente Datos del cliente a persistir.
     * @return El cliente guardado con su ID asignado.
     */
    @Transactional
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id Identificador del cliente a borrar.
     */
    @Transactional
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
