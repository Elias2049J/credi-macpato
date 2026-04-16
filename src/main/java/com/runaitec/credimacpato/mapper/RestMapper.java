package com.runaitec.credimacpato.mapper;

public interface RestMapper<T, RES, REQ> {
    T toEntity(REQ req);

    RES toResponse(T t);
}
