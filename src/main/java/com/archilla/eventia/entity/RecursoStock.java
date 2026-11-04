package com.archilla.eventia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa un Recurso del catálogo de Stock de EVENTIA.
 * Permite gestionar de forma unificada material de eventos (sonido, iluminación,
 * estructuras), equipo técnico, personal técnico y animadores.
 * Corresponde a la tabla 'recursos_stock' en MariaDB.
 */
@Entity
@Table(name = "recursos_stock")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "asignaciones")
@EqualsAndHashCode(exclude = "asignaciones")
public class RecursoStock {

    /**
     * Clave primaria autoincremental del recurso.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código o referencia interna única de inventario/catálogo (ej. "SND-001", "PER-001").
     */
    @NotBlank(message = "El código de referencia es obligatorio")
    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    /**
     * Nombre descriptivo del material, equipo o perfil profesional.
     */
    @NotBlank(message = "El nombre del recurso es obligatorio")
    @Column(nullable = false)
    private String nombre;

    /**
     * Categoría del recurso:
     * SONIDO, ILUMINACION, ESTRUCTURAS, EQUIPO_TECNICO, PERSONAL_TECNICO, ANIMADORES, OTROS.
     */
    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false, length = 40)
    private String categoria;

    /**
     * Especificaciones técnicas del equipo o descripción del perfil profesional.
     */
    @Column(length = 500)
    private String descripcion;

    /**
     * Cantidad total de unidades en almacén o efectivos disponibles en plantilla/bolsa.
     */
    @NotNull(message = "La cantidad total es obligatoria")
    @Min(value = 1, message = "La cantidad total debe ser al menos 1")
    @Column(name = "cantidad_total", nullable = false)
    private Integer cantidadTotal = 1;

    /**
     * Coste o tarifa unitaria de referencia por evento/jornada (€).
     */
    @Column(name = "coste_unitario", precision = 10, scale = 2)
    private BigDecimal costeUnitario;

    /**
     * Estado operativo del recurso: DISPONIBLE, EN_MANTENIMIENTO, BAJA.
     */
    @NotBlank(message = "El estado operativo es obligatorio")
    @Column(nullable = false, length = 30)
    private String estado = "DISPONIBLE";

    /**
     * Ubicación física en el almacén o procedencia del personal (ej. "Nave A - Estantería 3", "Plantilla Interna").
     */
    @Column(name = "ubicacion_almacen")
    private String ubicacionAlmacen;

    /**
     * Historial de asignaciones de este recurso a distintos eventos.
     */
    @OneToMany(mappedBy = "recurso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"recurso", "hibernateLazyInitializer", "handler"})
    private List<AsignacionEvento> asignaciones = new ArrayList<>();

    /**
     * Calcula cuántas unidades están actualmente comprometidas (RESERVADO o EN_USO)
     * en eventos activos (no finalizados).
     */
    public int getCantidadAsignada() {
        if (asignaciones == null || asignaciones.isEmpty()) {
            return 0;
        }
        return asignaciones.stream()
                .filter(a -> a.getCantidad() != null)
                .filter(a -> !"DEVUELTO".equalsIgnoreCase(a.getEstadoAsignacion()))
                .filter(a -> a.getEvento() == null || !"FINALIZADO".equalsIgnoreCase(a.getEvento().getEstado()))
                .mapToInt(AsignacionEvento::getCantidad)
                .sum();
    }

    /**
     * Calcula cuántas unidades quedan disponibles para asignar.
     */
    public int getCantidadDisponible() {
        if (!"DISPONIBLE".equalsIgnoreCase(estado)) {
            return 0;
        }
        int total = cantidadTotal != null ? cantidadTotal : 0;
        int libre = total - getCantidadAsignada();
        return Math.max(libre, 0);
    }

    /**
     * Indica si el recurso pertenece al bloque de Recursos Humanos (Personal Técnico o Animadores).
     */
    public boolean isPersonal() {
        return "PERSONAL_TECNICO".equalsIgnoreCase(categoria) || "ANIMADORES".equalsIgnoreCase(categoria);
    }
}
