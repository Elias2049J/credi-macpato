package com.runaitec.credimacpato.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "aporte")
@Getter
@Setter
public class Contribution extends Transaction {
    @Column(name = "obligatorio")
    private Boolean mandatory;
    @Enumerated(EnumType.STRING)
    @Column(name = "recurrencia")
    private Recurrency recurrency;
}
