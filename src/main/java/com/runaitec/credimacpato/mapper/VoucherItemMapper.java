package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.voucher.VoucherItemRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherItemResponseDTO;
import com.runaitec.credimacpato.entity.VoucherItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChargeMapper.class})
public interface VoucherItemMapper {

    @Mapping(source = "voucher.id", target = "voucherId")
    @Mapping(source = "payment.id", target = "paymentId")
    @Mapping(source = "chargeReason", target = "chargeReason")
    VoucherItemResponseDTO toResponseDto(VoucherItem entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "voucher", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "chargeReason", ignore = true)
    @Mapping(target = "payableAmount", ignore = true)
    @Mapping(target = "state", ignore = true)
    VoucherItem toEntity(VoucherItemRequestDTO dto);
}
