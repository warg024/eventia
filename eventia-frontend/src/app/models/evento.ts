import { Cliente } from './cliente';

/**
 * Modelo TypeScript para la entidad EventoProyecto.
 * Coincide con com.archilla.eventia.entity.EventoProyecto de Java.
 */
export interface EventoProyecto {
    id?: number;
    nombreEvento: string; // Ej. "Cabalgata de Reyes 2027"
    fechaInicio: string;  // Formato ISO 'YYYY-MM-DD'
    fechaFin: string;     // Formato ISO 'YYYY-MM-DD'
    presupuesto?: number; // Importe económico
    estado: 'PRESUPUESTADO' | 'CONFIRMADO' | 'EN_PROCESO' | 'FINALIZADO';
    ubicacion?: string;   // Recinto o plaza
    cliente: Cliente;     // Cliente contratante del evento
}