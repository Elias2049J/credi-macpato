package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "puesto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Integer number;

    @ManyToOne
    private Association association;

    @ManyToOne
    private Partner partner;

    @OneToMany
    private List<Debt> debts;

    @OneToMany
    private List<Voucher> vouchers;
}
