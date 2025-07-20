package com.eps.epsapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fecha;

    @Column(name = "monto", nullable = false)
    private Double valorTotal;

    @Column(name = "estado_pago", nullable = false)
    private Double estadoPago;

    @ManyToOne
    @JoinColumn(name = "id_afiliado", nullable = false)
    private Afiliado afiliado;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;
    }

