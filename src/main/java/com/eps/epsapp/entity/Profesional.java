package com.eps.epsapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profesional")
public class Profesional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesional")
    private Integer idProfesional;

    @ManyToOne
    @JoinColumn(name = "id_tercero", nullable = false)
    private Tercero tercero;

    @ManyToOne
    @JoinColumn(name = "id_centro_salud", nullable = false)
    private CentroSalud centroSalud;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Especialidad especialidad;


}
