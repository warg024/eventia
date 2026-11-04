package com.archilla.eventia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@ToString(exclude = {"cliente", "asignaciones"})
@EqualsAndHashCode(exclude = {"cliente", "asignaciones"})
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    /**
     * Fecha de finalización o desmontaje del evento.
     */
    @NotNull(message = "La fecha de fin es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    /**
     * Recursos de stock (material, equipo técnico, personal técnico, animadores) asignados al evento.
     */
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"evento", "hibernateLazyInitializer", "handler"})
    private List<AsignacionEvento> asignaciones = new ArrayList<>();
}
