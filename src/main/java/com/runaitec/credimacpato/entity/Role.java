package com.runaitec.credimacpato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    PRESIDENT("Presidente"),
    TREASURER("Tesorero"),
    SPOKESPERSON("Vocal");
    
    private final String displayName;
}

