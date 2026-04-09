package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}