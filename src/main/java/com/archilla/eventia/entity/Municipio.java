package com.archilla.eventia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA de referencia que representa un Municipio / Localidad.
 * Corresponde a la tabla 'municipios' en la base de datos MariaDB.
 *
 * Cada municipio pertenece obligatoriamente a una Provincia (@ManyToOne)
 * e incluye su código postal de referencia.
 */
@Entity
@Table(name = "municipios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"provincia", "clientes"})
@EqualsAndHashCode(exclude = {"provincia", "clientes"})
public class Municipio {

    /**
     * Clave primaria autoincremental del municipio.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre oficial del municipio (ej. "Albolote", "Granada", "Motril").
     */
    @NotBlank(message = "El nombre del municipio es obligatorio")
    @Column(nullable = false, length = 150)
    private String nombre;

    /**
     * Código postal predeterminado asociado al municipio (ej. "18220").
     */
    @NotBlank(message = "El código postal es obligatorio")
    @Column(name = "codigo_postal", nullable = false, length = 5)
    private String codigoPostal;

    /**
     * Provincia a la que pertenece el municipio.
     * Relación @ManyToOne: Varios municipios pertenecen a una misma provincia.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "provincia_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_municipios_provincias")
    )
    @JsonIgnoreProperties({"municipios", "hibernateLazyInitializer", "handler"})
    private Provincia provincia;

    /**
     * Clientes ubicados en este municipio.
     */
    @OneToMany(mappedBy = "municipio", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"municipio", "hibernateLazyInitializer", "handler"})
    private List<Cliente> clientes = new ArrayList<>();
}
