package com.runaitec.credimacpato.entity.user;

import com.runaitec.credimacpato.entity.Voucher;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<Voucher> issuedVouchers;

    @ManyToOne
    private Association association;
}
