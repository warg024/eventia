package com.archilla.eventia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA de referencia que representa una Provincia española (ej. "18 - Granada").
 * Corresponde a la tabla 'provincias' en la base de datos MariaDB.
 *
 * Sirve como catálogo maestro para normalizar direcciones y evitar errores tipográficos.
 */
@Entity
@Table(name = "provincias")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "municipios")
@EqualsAndHashCode(exclude = "municipios")
public class Provincia {

    /**
     * Clave primaria autoincremental de la provincia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código oficial de 2 dígitos de la provincia (ej. "18" para Granada, "28" para Madrid).
     */
    @NotBlank(message = "El código de provincia es obligatorio")
    @Column(nullable = false, unique = true, length = 2)
    private String codigo;

    /**
     * Nombre oficial de la provincia (ej. "Granada", "Málaga", "Jaén").
     */
    @NotBlank(message = "El nombre de la provincia es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Lista de municipios pertenecientes a esta provincia.
     */
    @OneToMany(mappedBy = "provincia", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"provincia", "hibernateLazyInitializer", "handler"})
    private List<Municipio> municipios = new ArrayList<>();
}
