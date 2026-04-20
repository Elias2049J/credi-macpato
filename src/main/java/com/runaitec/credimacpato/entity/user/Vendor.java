package com.runaitec.credimacpato.entity.user;

import com.runaitec.credimacpato.entity.Stand;
import com.runaitec.credimacpato.entity.Voucher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@Entity
@Table(name = "vendedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vendor extends User {

    @Column(name = "saldo", precision = 10, scale = 2)
    private BigDecimal moneyBalance = BigDecimal.ZERO.setScale(2, HALF_UP);

    @OneToMany(mappedBy = "issuer")
    private List<Voucher> emittedVouchers = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Stand> stands = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_asociacion", nullable = false)
    private Association association;

    @Override
    protected void onCreate() {
        super.onCreate();
        moneyBalance = moneyBalance == null
                ? BigDecimal.ZERO.setScale(2, HALF_UP)
                : moneyBalance.setScale(2, HALF_UP);
    }
}