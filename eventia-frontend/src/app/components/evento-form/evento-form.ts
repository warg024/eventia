import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { EventoService } from '../../services/evento.service';
import { ClienteService } from '../../services/cliente.service';
import { EventoProyecto } from '../../models/evento';
import { Cliente } from '../../models/cliente';

/**
 * Componente de formulario para la creación y edición de eventos y proyectos en EVENTIA.
 */
@Component({
  selector: 'app-evento-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './evento-form.html',
  styleUrl: './evento-form.css'
})
export class EventoFormComponent implements OnInit {

  private readonly eventoService = inject(EventoService);
  private readonly clienteService = inject(ClienteService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly cdr = inject(ChangeDetectorRef);

  // Objeto base para el formulario
  evento: EventoProyecto = {
    nombreEvento: '',
    fechaInicio: '',
    fechaFin: '',
    presupuesto: undefined,
    estado: 'PRESUPUESTADO',
    ubicacion: '',
    cliente: { razonSocial: '', cif: '' }
  };

  clientes: Cliente[] = [];
  esEdicion: boolean = false;
  cargando: boolean = false;
  guardando: boolean = false;
  errorMsg: string = '';

  ngOnInit(): void {
    this.cargarClientes();
    this.comprobarModoEdicion();
  }

  /**
   * Carga la lista de clientes para seleccionar en el desplegable.
   */
  cargarClientes(): void {
    this.clienteService.getClientes().subscribe({
      next: (data: any) => {
        this.clientes = Array.isArray(data) ? data : (data?.content || []);
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al cargar clientes:', err)
    });
  }

  /**
   * Comprueba si la URL contiene el parámetro ID para activar el modo edición.
   */
  comprobarModoEdicion(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam && idParam !== 'nuevo') {
      this.esEdicion = true;
      this.cargando = true;
      this.cdr.detectChanges();
      const eventoId = Number(idParam);

      this.eventoService.getEventoById(eventoId).subscribe({
        next: (data) => {
          this.evento = data;
          this.cargando = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error al cargar evento:', err);
          this.errorMsg = 'No se pudo cargar la información del evento.';
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
    } else {
      this.esEdicion = false;
      this.cargando = false;
      this.cdr.detectChanges();
    }
  }

  /**
   * Comparador para que el select reconozca el objeto Cliente seleccionado al editar.
   */
  compararClientes(c1?: Cliente, c2?: Cliente): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  /**
   * Envía los datos a Spring Boot para guardar o actualizar en MariaDB.
   */
  guardarEvento(): void {
    if (!this.evento.nombreEvento || !this.evento.fechaInicio || !this.evento.fechaFin || !this.evento.cliente?.id) {
      this.errorMsg = 'El nombre del evento, las fechas y el cliente contratante son obligatorios.';
      return;
    }

    this.guardando = true;
    this.errorMsg = '';
    this.cdr.detectChanges();

    const peticion = this.esEdicion && this.evento.id
      ? this.eventoService.actualizarEvento(this.evento.id, this.evento)
      : this.eventoService.crearEvento(this.evento);

    peticion.subscribe({
      next: () => {
        this.guardando = false;
        this.router.navigate(['/eventos']); // Volver a la lista de eventos
      },
      error: (err) => {
        console.error('Error al guardar evento:', err);
        this.errorMsg = 'Ocurrió un error al guardar el evento. Verifica los datos.';
        this.guardando = false;
        this.cdr.detectChanges();
      }
    });
  }
}
