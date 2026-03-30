package com.runaitec.credimacpato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoanRequestState {
    PENDING("Pendiente"),
    DENIED("Denegado"),
    APPROVED("Aprobado");

    private final String displayName;

    public boolean isPending() {
        return this == PENDING;
    }
    public boolean isDenied() {
        return this == DENIED;
    }
    public boolean isApproved() {
        return this == APPROVED;
    }
}
