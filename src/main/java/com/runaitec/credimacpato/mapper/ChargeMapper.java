package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.chargeReason.ChargeRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeResponseDTO;
import com.runaitec.credimacpato.entity.Charge;
import com.runaitec.credimacpato.entity.Stand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChargeMapper extends RestMapper<Charge, ChargeResponseDTO, ChargeRequestDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(source = "standId", target = "stand.id")
    Charge toEntity(ChargeRequestDTO dto);

    @Mapping(source = "stand.id", target = "standId")
    ChargeResponseDTO toResponse(Charge entity);

    default Stand mapStandId(Long id) {
        if (id == null) {
            return null;
        }
        Stand stand = new Stand();
        stand.setId(id);
        return stand;
    }
}
