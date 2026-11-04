import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Provincia, Municipio } from '../models/ubicacion';

/**
 * Servicio Angular para la consulta de provincias y municipios oficiales.
 * Se conecta con el controlador REST /api/v1/ubicaciones en Spring Boot.
 */
@Injectable({
  providedIn: 'root'
})
export class UbicacionService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/v1/ubicaciones';

  /**
   * Obtiene la lista completa de provincias.
   */
  getProvincias(): Observable<Provincia[]> {
    return this.http.get<Provincia[]>(`${this.apiUrl}/provincias`);
  }

  /**
   * Obtiene los municipios pertenecientes a una provincia específica.
   * @param provinciaId Identificador de la provincia seleccionada.
   */
  getMunicipiosPorProvincia(provinciaId: number): Observable<Municipio[]> {
    return this.http.get<Municipio[]>(`${this.apiUrl}/provincias/${provinciaId}/municipios`);
  }

  /**
   * Obtiene los datos de un municipio concreto por su ID.
   * @param id Identificador del municipio.
   */
  getMunicipioById(id: number): Observable<Municipio> {
    return this.http.get<Municipio>(`${this.apiUrl}/municipios/${id}`);
  }
}