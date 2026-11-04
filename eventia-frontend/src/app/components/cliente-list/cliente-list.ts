import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ClienteService } from '../../services/cliente.service';
import { Cliente } from '../../models/cliente';

/**
 * Componente para listar, buscar y eliminar clientes en EVENTIA.
 * Integrado con Tailwind CSS para una interfaz tipo Dashboard.
 */
@Component({
  selector: 'app-cliente-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cliente-list.html',
  styleUrl: './cliente-list.css'
})
export class ClienteListComponent implements OnInit {

  private readonly clienteService = inject(ClienteService);
  private readonly cdr = inject(ChangeDetectorRef);

  clientes: Cliente[] = [];
  textoBusqueda: string = '';
  cargando: boolean = false;
  errorMsg: string = '';

  ngOnInit(): void {
    this.cargarClientes();
  }

  /**
   * Obtiene la lista de clientes desde Spring Boot.
   */
  cargarClientes(): void {
    this.cargando = true;
    this.errorMsg = '';
    this.cdr.detectChanges();

    this.clienteService.getClientes(this.textoBusqueda).subscribe({
      next: (data: any) => {
        // Admite respuesta directa en Array o respuesta paginada de Spring Data (data.content)
        this.clientes = Array.isArray(data) ? data : (data?.content || []);
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al conectar con Spring Boot:', err);
        this.errorMsg = 'Error al cargar clientes desde MariaDB.';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  /**
   * Ejecuta la búsqueda al pulsar Enter o el botón de buscar.
   */
  buscar(): void {
    this.cargarClientes();
  }

  /**
   * Pide confirmación y borra un cliente por su ID.
   */
  eliminarCliente(id?: number): void {
    if (!id) return;

    if (confirm('¿Estás seguro de que deseas eliminar este cliente?')) {
      this.clienteService.eliminarCliente(id).subscribe({
        next: () => {
          this.cargarClientes(); // Recargar la tabla
        },
        error: (err) => {
          console.error('Error al eliminar cliente:', err);
          alert('No se pudo eliminar el cliente. Comprueba si tiene eventos asociados.');
        }
      });
    }
  }
}
