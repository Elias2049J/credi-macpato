package com.runaitec.credimacpato.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deudas_socio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDebtReport extends Report {
}