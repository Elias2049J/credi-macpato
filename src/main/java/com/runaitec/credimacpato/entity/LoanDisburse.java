package com.runaitec.credimacpato.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "desembolso_prestamo")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class LoanDisburse extends Transaction {
}

