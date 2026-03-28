package com.runaitec.credimacpato.usecase;

import java.util.List;

public interface CrudUseCase<DTO,T, ID> {
    DTO create(DTO dto);
    DTO update(DTO dto);
    T getEntityById(ID id);
    DTO getById(ID id);
    List<DTO>findAll();
}