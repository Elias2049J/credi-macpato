package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.AccountDTO;
import com.runaitec.credimacpato.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "accountNumber", source = "accountNumber"),
        @Mapping(target = "openingDate", source = "openingDate"),
        @Mapping(target = "amount", source = "amount"),
        @Mapping(target = "accountState", source = "accountState")
    })
    AccountDTO toDto(Account entity);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "accountNumber", source = "accountNumber"),
        @Mapping(target = "openingDate", source = "openingDate"),
        @Mapping(target = "amount", source = "amount"),
        @Mapping(target = "accountState", source = "accountState")
    })
    Account toEntity(AccountDTO dto);
}
