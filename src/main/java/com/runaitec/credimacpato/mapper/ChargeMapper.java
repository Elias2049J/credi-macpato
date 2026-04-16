package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.chargeReason.ChargeRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeResponseDTO;
import com.runaitec.credimacpato.entity.Charge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChargeMapper extends RestMapper<Charge, ChargeResponseDTO, ChargeRequestDTO> {
    @Mapping(target = "id", ignore = true)
    Charge toEntity(ChargeRequestDTO dto);
}

