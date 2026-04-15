package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.chargeReason.ChargeReasonRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeReasonResponseDTO;
import com.runaitec.credimacpato.entity.ChargeReason;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChargeReasonMapper {

    ChargeReasonResponseDTO toResponseDto(ChargeReason entity);

    @Mapping(target = "id", ignore = true)
    ChargeReason toEntity(ChargeReasonRequestDTO dto);
}

