package com.runaitec.credimacpato.mapper;

public interface RestMapper<T, RES, REQ> {
    T toEntity(REQ req);

    RES toResponse(T t);

    default void updateEntity(T currentEntity, REQ req) {
        if (currentEntity == null) {
            throw new IllegalArgumentException("Entidad actual requerida");
        }
    }
}
