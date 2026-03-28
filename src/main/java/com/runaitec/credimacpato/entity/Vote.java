package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "voto")
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "solicitud_prestamo_id")
    private LoanRequest loanRequest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private User user;

    @Column(name = "aprobado")
    private Boolean approved;
}