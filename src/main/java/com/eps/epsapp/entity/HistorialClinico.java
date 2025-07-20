package com.eps.epsapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historialclinico")
public class HistorialClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorialClinico;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita idCita;

    @ManyToOne
    @JoinColumn(name = "id_profesional", nullable = false)
    private Profesional idProfesional;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fecha;

    @Column(name = "diagnostico", nullable = false)
    private String diagnostico;

    @Column(name = "tratamiento", nullable = false)
    private String tratamiento;


    }

