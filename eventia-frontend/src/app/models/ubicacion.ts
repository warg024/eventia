/**
 * Modelo TypeScript para la entidad Provincia.
 * Coincide con com.archilla.eventia.entity.Provincia de Java.
 */
export interface Provincia {
    id?: number;
    codigo: string; // Ej. "18"
    nombre: string; // Ej. "Granada"
}

/**
 * Modelo TypeScript para la entidad Municipio.
 * Coincide con com.archilla.eventia.entity.Municipio de Java.
 */
export interface Municipio {
    id?: number;
    nombre: string;        // Ej. "Albolote"
    codigoPostal: string;  // Ej. "18220"
    provincia?: Provincia; // Relación con la provincia
}