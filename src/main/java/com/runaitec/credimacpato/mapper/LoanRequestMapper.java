package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.LoanRequestDTO;
import com.runaitec.credimacpato.entity.LoanRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {VoteMapper.class})
public interface LoanRequestMapper {
    LoanRequestDTO toDto(LoanRequest entity);
    LoanRequest toEntity(LoanRequestDTO dto);
}
