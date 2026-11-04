import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventoProyecto } from '../models/evento';

/**
 * Servicio Angular para la gestión de Eventos y Proyectos.
 * Se comunica con los endpoints REST del backend Spring Boot.
 */
@Injectable({
    providedIn: 'root'
})
export class EventoService {

    private readonly http = inject(HttpClient);
    private readonly apiUrl = 'http://localhost:8080/api/v1/eventos';

    /**
     * Obtiene la lista completa de eventos.
     * Permite filtrado opcional por estado o ID de cliente.
     * @param estado Estado del evento (ej: 'CONFIRMADO', 'PRESUPUESTADO').
     * @param clienteId ID del cliente contratante.
     */
    getEventos(estado?: string, clienteId?: number): Observable<EventoProyecto[]> {
        let params = new HttpParams();
        if (estado && estado.trim() !== '') {
            params = params.set('estado', estado.trim());
        }
        if (clienteId) {
            params = params.set('clienteId', clienteId.toString());
        }
        return this.http.get<EventoProyecto[]>(this.apiUrl, { params });
    }

    /**
     * Obtiene los eventos programados en un rango de fechas.
     * @param inicio Fecha de inicio (YYYY-MM-DD).
     * @param fin Fecha de fin (YYYY-MM-DD).
     */
    getEventosPorRangoFechas(inicio: string, fin: string): Observable<EventoProyecto[]> {
        const params = new HttpParams()
            .set('inicio', inicio)
            .set('fin', fin);
        return this.http.get<EventoProyecto[]>(`${this.apiUrl}/rango-fechas`, { params });
    }

    /**
     * Obtiene el detalle de un evento por su ID.
     * @param id Identificador del evento.
     */
    getEventoById(id: number): Observable<EventoProyecto> {
        return this.http.get<EventoProyecto>(`${this.apiUrl}/${id}`);
    }

    /**
     * Crea un nuevo evento en el sistema.
     * @param evento Objeto con los datos del nuevo evento.
     */
    crearEvento(evento: EventoProyecto): Observable<EventoProyecto> {
        return this.http.post<EventoProyecto>(this.apiUrl, evento);
    }

    /**
     * Actualiza un evento existente.
     * @param id Identificador del evento a actualizar.
     * @param evento Datos actualizados del evento.
     */
    actualizarEvento(id: number, evento: EventoProyecto): Observable<EventoProyecto> {
        return this.http.put<EventoProyecto>(`${this.apiUrl}/${id}`, evento);
    }

    /**
     * Elimina un evento por su ID.
     * @param id Identificador del evento a borrar.
     */
    eliminarEvento(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}