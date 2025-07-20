package com.eps.epsapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "valor_pagado", nullable = false)
    private Double valorPagado;

    @Column(name = "medio_pago", nullable = false)
    private String medioPago;
}
