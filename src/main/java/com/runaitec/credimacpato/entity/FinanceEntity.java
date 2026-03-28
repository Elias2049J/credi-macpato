package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cooperativa")
@Getter
@Setter
public class FinanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "direccion")
    private String address;

    @Column(name = "fecha_creacion")
    private LocalDateTime creationDateTime;

    @OneToOne
    @JoinColumn(name = "cuenta_id")
    private Account account;

    @OneToOne
    private BoardOfDirectors boardOfDirectors;
}
