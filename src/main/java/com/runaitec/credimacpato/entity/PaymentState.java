package com.runaitec.credimacpato.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentState {
    PENDING("Pendiente"),
    PAID("Pagado"),
    PARTIALLY_PAID("Parcialmente pagado"),
    CANCELED("Cancelado");

    private final String displayName;
}
