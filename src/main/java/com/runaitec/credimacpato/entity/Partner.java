package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "socio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partner extends PersonUser {
    @OneToOne
    private Account account;

    @ManyToOne
    private Association association;

    @OneToMany
    private List<Debt> debts;
}