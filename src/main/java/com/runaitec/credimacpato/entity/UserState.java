package com.runaitec.credimacpato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    ACTIVE("Activo"),
    BLOCKED("Cerrado"),
    UNACTIVE("Bloqueado");

    private final String displayName;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }
}
