import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Provincia, Municipio } from '../models/ubicacion';

/**
 * Servicio Angular para la consulta de catálogos maestros de Ubicaciones (Provincias y Municipios).
 */
@Injectable({
  providedIn: 'root'
})
export class UbicacionService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/v1/ubicaciones';

  /**
   * Obtiene la lista ordenada de provincias.
   */
  getProvincias(): Observable<Provincia[]> {
    return this.http.get<Provincia[]>(`${this.apiUrl}/provincias`);
  }

  /**
   * Obtiene los municipios de una provincia específica.
   * @param provinciaId ID de la provincia.
   */
  getMunicipiosPorProvincia(provinciaId: number): Observable<Municipio[]> {
    return this.http.get<Municipio[]>(`${this.apiUrl}/provincias/${provinciaId}/municipios`);
  }

  /**
   * Obtiene el detalle de un municipio por su ID.
   * @param id ID del municipio.
   */
  getMunicipioById(id: number): Observable<Municipio> {
    return this.http.get<Municipio>(`${this.apiUrl}/municipios/${id}`);
  }
}

