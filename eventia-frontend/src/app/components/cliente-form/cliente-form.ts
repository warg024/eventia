import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ClienteService } from '../../services/cliente.service';
import { UbicacionService } from '../../services/ubicacion.service';
import { Cliente } from '../../models/cliente';
import { Provincia, Municipio } from '../../models/ubicacion';

@Component({
  selector: 'app-cliente-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cliente-form.html',
  styleUrl: './cliente-form.css'
})
export class ClienteFormComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly clienteService = inject(ClienteService);
  private readonly ubicacionService = inject(UbicacionService);
  private readonly cdr = inject(ChangeDetectorRef);

  // Inicialización del modelo vacío para evitar nulos
  cliente: Cliente = {
    razonSocial: '',
    cif: '',
    personaContacto: '',
    email: '',
    telefono: '',
    direccionCalle: ''
  };

  provincias: Provincia[] = [];
  municipios: Municipio[] = [];
  provinciaSeleccionadaId?: number;

  esEdicion: boolean = false;
  cargando: boolean = false;
  guardando: boolean = false;
  errorMsg: string = '';

  ngOnInit(): void {
    this.cargarProvincias();

    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam && idParam !== 'nuevo') {
      this.esEdicion = true;
      this.cargarCliente(Number(idParam));
    } else {
      this.esEdicion = false;
      this.cargando = false;
      this.cdr.detectChanges();
    }
  }

  cargarProvincias(): void {
    this.ubicacionService.getProvincias().subscribe({
      next: (data: any) => {
        this.provincias = Array.isArray(data) ? data : (data?.content || []);
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al cargar provincias:', err)
    });
  }

  onProvinciaChange(provinciaId?: number): void {
    this.provinciaSeleccionadaId = provinciaId;
    this.municipios = [];
    this.cdr.detectChanges();

    if (provinciaId) {
      this.ubicacionService.getMunicipiosPorProvincia(provinciaId).subscribe({
        next: (data: any) => {
          this.municipios = Array.isArray(data) ? data : (data?.content || []);
          this.cdr.detectChanges();
        },
        error: (err) => console.error('Error al cargar municipios:', err)
      });
    }
  }

  compararMunicipios(m1?: Municipio, m2?: Municipio): boolean {
    return m1 && m2 ? m1.id === m2.id : m1 === m2;
  }

  cargarCliente(id: number): void {
    this.cargando = true;
    this.errorMsg = '';
    this.cdr.detectChanges();

    this.clienteService.getClienteById(id).subscribe({
      next: (data) => {
        this.cliente = data;
        this.cargando = false;

        // Si el cliente ya tiene provincia asignada a través del municipio, cargamos sus municipios
        if (data.municipio?.provincia?.id) {
          this.provinciaSeleccionadaId = data.municipio.provincia.id;
          this.onProvinciaChange(this.provinciaSeleccionadaId);
        }
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al recuperar cliente por ID:', err);
        this.errorMsg = 'No se pudo cargar la ficha del cliente. Verifica la conexión con Spring Boot.';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  guardar(): void {
    this.guardando = true;
    this.errorMsg = '';
    this.cdr.detectChanges();

    const peticion = (this.esEdicion && this.cliente.id)
      ? this.clienteService.actualizarCliente(this.cliente.id, this.cliente)
      : this.clienteService.crearCliente(this.cliente);

    peticion.subscribe({
      next: () => {
        this.guardando = false;
        this.router.navigate(['/clientes']);
      },
      error: (err) => {
        console.error('Error al guardar cliente:', err);
        this.errorMsg = 'Error al guardar el cliente en MariaDB. Verifica los datos.';
        this.guardando = false;
        this.cdr.detectChanges();
      }
    });
  }
}
