import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { EventoService } from '../../services/evento.service';
import { ClienteService } from '../../services/cliente.service';
import { EventoProyecto } from '../../models/evento';
import { Cliente } from '../../models/cliente';

/**
 * Componente para listar, filtrar y gestionar eventos y proyectos en EVENTIA.
 * Incluye badges de color dinámicos según el estado del proyecto.
 */
@Component({
  selector: 'app-evento-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './evento-list.html',
  styleUrl: './evento-list.css'
})
export class EventoListComponent implements OnInit {

  private readonly eventoService = inject(EventoService);
  private readonly clienteService = inject(ClienteService);
  private readonly cdr = inject(ChangeDetectorRef);

  eventos: EventoProyecto[] = [];
  clientes: Cliente[] = [];

  // Filtros de búsqueda
  estadoFiltro: string = '';
  clienteIdFiltro?: number;

  cargando: boolean = false;
  errorMsg: string = '';

  ngOnInit(): void {
    this.cargarClientesFiltro();
    this.cargarEventos();
  }

  /**
   * Carga el desplegable de clientes para el filtro.
   */
  cargarClientesFiltro(): void {
    this.clienteService.getClientes().subscribe({
      next: (data: any) => {
        this.clientes = Array.isArray(data) ? data : (data?.content || []);
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al cargar clientes para filtro:', err)
    });
  }

  /**
   * Obtiene los eventos desde Spring Boot aplicando los filtros activos.
   */
  cargarEventos(): void {
    this.cargando = true;
    this.errorMsg = '';
    this.cdr.detectChanges();

    this.eventoService.getEventos(this.estadoFiltro, this.clienteIdFiltro).subscribe({
      next: (data: any) => {
        // Soporta respuesta directa en Array o paginada en data.content
        this.eventos = Array.isArray(data) ? data : (data?.content || []);
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al conectar con Spring Boot:', err);
        this.errorMsg = 'No se pudieron cargar los eventos. Revisa la conexión con el servidor.';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  /**
   * Aplica o limpia los filtros de búsqueda.
   */
  aplicarFiltros(): void {
    this.cargarEventos();
  }

  limpiarFiltros(): void {
    this.estadoFiltro = '';
    this.clienteIdFiltro = undefined;
    this.cargarEventos();
  }

  /**
   * Elimina un evento tras confirmación.
   */
  eliminarEvento(id?: number): void {
    if (!id) return;

    if (confirm('¿Deseas eliminar este evento de la programación?')) {
      this.eventoService.eliminarEvento(id).subscribe({
        next: () => this.cargarEventos(),
        error: (err) => {
          console.error('Error al borrar evento:', err);
          alert('No se pudo eliminar el evento.');
        }
      });
    }
  }

  /**
   * Devuelve las clases CSS de Tailwind según el estado del evento.
   */
  obtenerClaseBadgeEstado(estado: string): string {
    switch (estado) {
      case 'PRESUPUESTADO':
        return 'bg-amber-100 text-amber-800 border-amber-200';
      case 'CONFIRMADO':
        return 'bg-blue-100 text-blue-800 border-blue-200';
      case 'EN_PROCESO':
        return 'bg-purple-100 text-purple-800 border-purple-200';
      case 'FINALIZADO':
        return 'bg-emerald-100 text-emerald-800 border-emerald-200';
      default:
        return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  }
}
