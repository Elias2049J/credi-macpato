package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.LoanDTO;
import com.runaitec.credimacpato.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {PaymentMapper.class, UserMapper.class})
public interface LoanMapper {
    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "dueDate", source = "dueDate"),
        @Mapping(target = "startDate", source = "startDate"),
        @Mapping(target = "termMonths", source = "termMonths"),
        @Mapping(target = "interestRate", source = "interestRate"),
        @Mapping(target = "payments", source = "payments"),
        @Mapping(target = "user", source = "user")
    })
    LoanDTO toDto(Loan entity);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "dueDate", source = "dueDate"),
        @Mapping(target = "startDate", source = "startDate"),
        @Mapping(target = "termMonths", source = "termMonths"),
        @Mapping(target = "interestRate", source = "interestRate"),
        @Mapping(target = "payments", source = "payments"),
        @Mapping(target = "user", source = "user")
    })
    Loan toEntity(LoanDTO dto);
}
