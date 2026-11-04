import { Routes } from '@angular/router';
import { ClienteListComponent } from './components/cliente-list/cliente-list';
import { EventoListComponent } from './components/evento-list/evento-list';
import { ClienteFormComponent } from './components/cliente-form/cliente-form';
import { EventoFormComponent } from './components/evento-form/evento-form';

export const routes: Routes = [
  // Ruta por defecto: redirige a la lista de clientes
  { path: '', redirectTo: 'clientes', pathMatch: 'full' },

  // Rutas del módulo de Clientes
  { path: 'clientes', component: ClienteListComponent },
  { path: 'clientes/nuevo', component: ClienteFormComponent },
  { path: 'clientes/editar/:id', component: ClienteFormComponent },

  // Ruta del módulo de Eventos
  { path: 'eventos', component: EventoListComponent },
  { path: 'eventos/nuevo', component: EventoFormComponent },      
  { path: 'eventos/editar/:id', component: EventoFormComponent },   
  // Comodín: cualquier URL desconocida redirige a clientes
  { path: '**', redirectTo: 'clientes' }
];