package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.stand.StandRequestDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.entity.Stand;
import com.runaitec.credimacpato.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandMapper extends RestMapper<Stand, StandResponseDTO, StandRequestDTO> {

    @Mapping(source = "owner.id", target = "ownerId")
    StandResponseDTO toResponse(Stand entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "vouchers", ignore = true)
    Stand toEntity(StandRequestDTO dto);

    default Long mapVoucherToId(Voucher voucher) {
        return voucher == null ? null : voucher.getId();
    }
}
