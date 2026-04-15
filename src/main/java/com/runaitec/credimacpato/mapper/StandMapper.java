package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.stand.StandRequestDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.entity.Stand;
import com.runaitec.credimacpato.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandMapper {

    @Mapping(source = "partner.id", target = "ownerId")
    @Mapping(source = "vouchers", target = "voucherIds")
    StandResponseDTO toResponseDto(Stand entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "partner", ignore = true)
    @Mapping(target = "vouchers", ignore = true)
    Stand toEntity(StandRequestDTO dto);

    default Integer mapVoucherToId(Voucher voucher) {
        return voucher == null ? null : voucher.getId();
    }
}
