import { Municipio } from './ubicacion';

/**
 * Modelo TypeScript para la entidad Cliente.
 * Coincide con com.archilla.eventia.entity.Cliente de Java.
 */
export interface Cliente {
    id?: number;
    razonSocial: string;      // Obligatorio: Nombre o Razón Social
    cif: string;              // Obligatorio: CIF/NIF único
    personaContacto?: string; // Opcional
    email?: string;           // Opcional
    telefono?: string;        // Opcional
    direccionCalle?: string;  // Opcional: Calle y número
    municipio?: Municipio;    // Municipio oficial seleccionado
}