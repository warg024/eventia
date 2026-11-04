package com.archilla.eventia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad JPA que representa un Evento o Proyecto organizado por EVENTIA.
 * Corresponde a la tabla 'eventos' en MariaDB.
 *
 * Mantiene una relación de clave foránea (@ManyToOne) con la entidad Cliente.
 */
@Entity
@Table(name = "eventos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "cliente")
@EqualsAndHashCode(exclude = "cliente")
public class EventoProyecto {

    /**
     * Clave primaria autoincremental del evento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre descriptivo del evento (ej. "Cabalgata de Reyes 2027").
     */
    @NotBlank(message = "El nombre del evento es obligatorio")
    @Column(name = "nombre_evento", nullable = false)
    private String nombreEvento;

    /**
     * Fecha de inicio de las actuaciones o montaje en el recinto.
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    /**
     * Fecha de finalización o desmontaje del evento.
     */
    @NotNull(message = "La fecha de fin es obligatoria")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    /**
     * Importe económico total presupuestado para el proyecto.
     * Se utiliza BigDecimal para garantizar precisión decimal en cálculos monetarios.
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal presupuesto;

    /**
     * Estado del ciclo de vida del proyecto (ej: PRESUPUESTADO, CONFIRMADO, EN_PROCESO, FINALIZADO).
     */
    @NotBlank(message = "El estado del evento es obligatorio")
    private String estado;

    /**
     * Recinto, plaza o dirección donde se celebra el evento.
     */
    private String ubicacion;

    /**
     * Cliente que solicita y financia el evento.
     *
     * Relación @ManyToOne: Varios eventos pueden pertenecer al mismo cliente.
     * @JoinColumn define la clave foránea 'cliente_id' con un nombre legible (foreignKey).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"eventos", "hibernateLazyInitializer", "handler"})
    @JoinColumn(
            name = "cliente_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_eventos_clientes")
    )
    private Cliente cliente;
}
