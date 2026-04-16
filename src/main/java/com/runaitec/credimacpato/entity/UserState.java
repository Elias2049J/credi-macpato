package com.runaitec.credimacpato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    ENABLED("Habilitado"),
    BLOCKED("Bloqueado"),
    DISABLED("Deshabilitado");

    private final String displayName;

    public boolean isActive() {
        return this == ENABLED;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }
}
