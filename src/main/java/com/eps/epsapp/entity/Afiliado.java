package com.eps.epsapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "afiliado")
public class Afiliado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_afiliado")
    private Integer idAfiliado;

    @ManyToOne
    @JoinColumn(name = "id_tercero", nullable = false)
    private Tercero tercero;

    @Column(name = "fecha_afiliacion", nullable = false)
    private LocalDate fechaAfiliacion;

    @ManyToOne
    @JoinColumn(name = "id_plan_salud", nullable = false)
    private PlanSalud planSalud;

}
