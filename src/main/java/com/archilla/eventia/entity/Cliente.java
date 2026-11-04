package com.archilla.eventia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa a un Cliente dentro del sistema EVENTIA.
 * Corresponde a la tabla 'clientes' en la base de datos MariaDB.
 *
 * Contiene los datos fiscales y de contacto de ayuntamientos, empresas
 * y particulares que contratan los servicios de eventos.
 */
@Entity
@Table(name = "clientes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"municipio", "eventos"})
@EqualsAndHashCode(exclude = {"municipio", "eventos"})
public class Cliente {

    // Clave primaria autoincremental de la tabla 'clientes'.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Razón social o nombre comercial del cliente (ej. "Ayuntamiento de Albolote").
     * Campo obligatorio a nivel de validación Java y restricción de base de datos.
     */
    @NotBlank(message = "La razón social es obligatoria")
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    /**
     * Cif/NIF/DNI fiscal del cliente.
     * Debe ser único en toda la base de datos para evitar duplicidades de clientes.
     */
    @NotBlank(message = "El CIF/NIF es obligatorio")
    @Column(nullable = false, unique = true, length = 20)
    private String cif;

    // Nombre y apellidos del interlocutor o persona de contacto en la empresa/entidad.
    @Column(name = "persona_contacto")
    private String personaContacto;

    // Correo electrónico de contacto corporativo para envío de presupuestos y facturas.
    @Email(message = "Debe proporcionar un email válido")
    private String email;

    // Teléfono principal de contacto para avisos de producción y montaje.
    private String telefono;

    /**
     * Dirección de la vía o calle (ej. "Calle Real, Nº 14").
     */
    @Column(name = "direccion_calle")
    private String direccionCalle;

    /**
     * Municipio oficial validado seleccionado para el cliente.
     * A través del municipio se obtiene automáticamente su provincia y código postal oficial.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"clientes", "hibernateLazyInitializer", "handler"})
    @JoinColumn(
            name = "municipio_id",
            foreignKey = @ForeignKey(name = "fk_clientes_municipios")
    )
    private Municipio municipio;

    /**
     * Historial de eventos y proyectos contratados por el cliente.
     * Relación bidireccional con EventoProyecto evitando recursión Jackson.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"cliente", "hibernateLazyInitializer", "handler"})
    private List<EventoProyecto> eventos = new ArrayList<>();
}
