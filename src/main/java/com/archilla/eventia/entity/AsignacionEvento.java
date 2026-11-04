package com.archilla.eventia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad JPA que representa la asignación de un recurso de stock (material,
 * equipo técnico, personal técnico o animadores) a un Evento o Proyecto concreto.
 * Corresponde a la tabla 'asignaciones_evento' en MariaDB.
 */
@Entity
@Table(name = "asignaciones_evento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"evento", "recurso"})
@EqualsAndHashCode(exclude = {"evento", "recurso"})
public class AsignacionEvento {

    /**
     * Clave primaria autoincremental de la asignación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Evento o proyecto al que se asigna el material o personal.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"asignaciones", "hibernateLazyInitializer", "handler"})
    @JoinColumn(
            name = "evento_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_asignaciones_eventos")
    )
    private EventoProyecto evento;

    /**
     * Recurso del catálogo de stock asignado.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"asignaciones", "hibernateLazyInitializer", "handler"})
    @JoinColumn(
            name = "recurso_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_asignaciones_recursos")
    )
    private RecursoStock recurso;

    /**
     * Número de unidades o efectivos asignados al evento.
     */
    @NotNull(message = "Debe indicar la cantidad a asignar")
    @Min(value = 1, message = "La cantidad asignada debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad = 1;

    /**
     * Fecha en la que se registra o planifica la salida/asignación.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;

    /**
     * Estado de la asignación: RESERVADO, EN_USO, DEVUELTO.
     */
    @NotBlank(message = "El estado de la asignación es obligatorio")
    @Column(name = "estado_asignacion", nullable = false, length = 30)
    private String estadoAsignacion = "RESERVADO";

    /**
     * Técnico responsable de la retirada/montaje o coordinador asignado.
     */
    @Column(length = 120)
    private String responsable;

    /**
     * Notas operativas (horarios, turnos de animadores, instrucciones de montaje, etc.).
     */
    @Column(length = 500)
    private String observaciones;

    /**
     * Calcula el coste estimado de esta partida (cantidad * costeUnitario del recurso).
     */
    public BigDecimal getCosteEstimado() {
        if (recurso == null || recurso.getCosteUnitario() == null || cantidad == null) {
            return BigDecimal.ZERO;
        }
        return recurso.getCosteUnitario().multiply(BigDecimal.valueOf(cantidad));
    }
}
