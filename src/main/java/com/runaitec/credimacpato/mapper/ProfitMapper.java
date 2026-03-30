package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.ProfitDTO;
import com.runaitec.credimacpato.entity.Profit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfitMapper {
    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "amount", source = "amount"),
        @Mapping(target = "user", source = "user"),
        @Mapping(target = "localDateTime", source = "localDateTime"),
        @Mapping(target = "totalContribution", source = "totalContribution"),
        @Mapping(target = "proportion", source = "proportion")
    })
    ProfitDTO toDto(Profit entity);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "amount", source = "amount"),
        @Mapping(target = "user", source = "user"),
        @Mapping(target = "localDateTime", source = "localDateTime"),
        @Mapping(target = "totalContribution", source = "totalContribution"),
        @Mapping(target = "proportion", source = "proportion")
    })
    Profit toEntity(ProfitDTO dto);
}
