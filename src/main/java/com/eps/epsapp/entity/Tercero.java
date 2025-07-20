package com.eps.epsapp.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tercero")
public class Tercero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tercero")
    private Integer idTercero;

    @Column(name = "tipo_identificacion", nullable = false)
    private String tipoDocumento;

    @Column(name = "numero_identificacion", nullable = false, unique = true)
    private String numeroDocumento;

    @Column(name = "nombres", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellido;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "correo", nullable = false)
    private String correo;
}
