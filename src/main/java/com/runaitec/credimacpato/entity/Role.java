package com.runaitec.credimacpato.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {
    VENDOR("Comerciante"),
    CUSTOMER("Cliente"),
    ASSOCIATION("Asociación");
    
    private final String displayName;
}