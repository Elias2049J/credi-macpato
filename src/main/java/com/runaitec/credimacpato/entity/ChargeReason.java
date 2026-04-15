package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "motivo_cobro",
        indexes = {
                @Index(name = "idx_motivo_cobro_nombre", columnList = "nombre")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "descripcion")
    private String description;
}

