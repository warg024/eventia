import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente';

/**
 * Servicio Angular para la gestión de Clientes.
 * Realiza las llamadas HTTP (CRUD) al backend Spring Boot.
 */
@Injectable({
    providedIn: 'root'
})
export class ClienteService {

    private readonly http = inject(HttpClient);
    private readonly apiUrl = 'http://localhost:8080/api/v1/clientes';

    /**
     * Obtiene el listado completo de clientes.
     * Admite un filtro opcional por razón social.
     * @param buscar Texto opcional para buscar por razón social.
     */
    getClientes(buscar?: string): Observable<Cliente[]> {
        let params = new HttpParams();
        if (buscar && buscar.trim() !== '') {
            params = params.set('buscar', buscar.trim());
        }
        return this.http.get<Cliente[]>(this.apiUrl, { params });
    }

    /**
     * Obtiene un cliente por su ID primario.
     * @param id Identificador del cliente.
     */
    getClienteById(id: number): Observable<Cliente> {
        return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
    }

    /**
     * Envía un nuevo cliente al backend para guardarlo en MariaDB.
     * @param cliente Datos del cliente a crear.
     */
    crearCliente(cliente: Cliente): Observable<Cliente> {
        return this.http.post<Cliente>(this.apiUrl, cliente);
    }

    /**
     * Actualiza los datos de un cliente existente.
     * @param id Identificador del cliente a modificar.
     * @param cliente Objeto con los datos actualizados.
     */
    actualizarCliente(id: number, cliente: Cliente): Observable<Cliente> {
        return this.http.put<Cliente>(`${this.apiUrl}/${id}`, cliente);
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     * @param id Identificador del cliente a borrar.
     */
    eliminarCliente(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}