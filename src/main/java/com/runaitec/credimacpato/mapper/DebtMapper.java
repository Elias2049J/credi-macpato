package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.entity.Debt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {PaymentMapper.class, UserMapper.class})
public interface DebtMapper {
    DebtDto toDto(Debt entity);
    Debt toEntity(LoanDTO dto);
}
