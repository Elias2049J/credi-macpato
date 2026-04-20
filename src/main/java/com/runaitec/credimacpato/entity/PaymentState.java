package com.runaitec.credimacpato.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentState {
    PENDING("Pendiente"),
    PAID("Pagado"),
    PARTIALLY_PAID("Parcialmente pagado"),
    CANCELLED("Cancelado");

    private final String displayName;

    public boolean isPaid() {
        return this == PAID;
    }

    public boolean idPending() {
        return this == PENDING;
    }
    public boolean isCancelled() {
        return this == PAID;
    }
    public boolean isPartiallyPaid() {
        return this == PARTIALLY_PAID;
    }
}
