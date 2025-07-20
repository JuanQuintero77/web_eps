package com.eps.epsapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    @ManyToOne
    @JoinColumn(name = "id_afiliado", nullable = false)
    private Afiliado afiliado;

    @ManyToOne
    @JoinColumn(name = "id_profesional", nullable = false)
    private Profesional profesional;

    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_cita", nullable = false)
    private LocalTime hora;

    @Column(name = "motivo_consulta", nullable = false)
    private String motivo;

    @Column(name = "estado_cita", nullable = false)
    private String estadoCita;

    }
