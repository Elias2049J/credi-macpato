package com.runaitec.credimacpato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountState {
    ACTIVE("Activo"),
    CLOSED("Cerrado"),
    BLOCKED("Bloqueado");

    private final String displayName;

    public boolean isActive() {
        return this == ACTIVE;
    }
}
