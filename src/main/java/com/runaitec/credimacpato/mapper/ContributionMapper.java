package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.ContributionDTO;
import com.runaitec.credimacpato.entity.Contribution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface ContributionMapper {
    ContributionDTO toDto(Contribution entity);
    Contribution toEntity(ContributionDTO dto);
}
