package com.runaitec.credimacpato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    PARTNER("Socio"),
    CUSTOMER("Cliente");
    
    private final String displayName;
}