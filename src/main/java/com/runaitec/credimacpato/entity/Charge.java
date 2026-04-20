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
                @Index(name = "idx_motivo_cobro_descripcion", columnList = "descripcion")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Charge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descripcion")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private Stand stand;

    @Column(name = "activo")
    private Boolean active = true;

    @PrePersist
    private void onCreate(){
        if (active == null)  {
            active = true;
        }
    }
}
