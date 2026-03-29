package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.ProfitDTO;
import com.runaitec.credimacpato.entity.Profit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfitMapper {
    ProfitDTO toDto(Profit entity);
    Profit toEntity(ProfitDTO dto);
}
