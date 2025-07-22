package com.eps.epsapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "planesalud")
public class PlanSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan_salud")
    private Integer idPlanSalud;

    @Column(name = "nombre_plan", nullable = false)
    private String nombrePlan;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "costo_mensual", nullable = false)
    private String costoMensual;

    }
