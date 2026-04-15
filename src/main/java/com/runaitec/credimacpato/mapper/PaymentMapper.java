package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.payment.PaymentRequestDTO;
import com.runaitec.credimacpato.dto.payment.PaymentResponseDTO;
import com.runaitec.credimacpato.entity.Payment;
import com.runaitec.credimacpato.entity.VoucherItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "voucher.id", target = "voucherId")
    @Mapping(source = "paidItems", target = "paidItemIds")
    PaymentResponseDTO toResponseDto(Payment entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "dateTime", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "voucher", ignore = true)
    @Mapping(source = "paidItemIds", target = "paidItems")
    Payment toEntity(PaymentRequestDTO dto);

    default Long mapVoucherItemToId(VoucherItem item) {
        return item == null ? null : item.getId();
    }

    default VoucherItem mapVoucherItemIdToEntity(Long id) {
        if (id == null) {
            return null;
        }
        VoucherItem item = new VoucherItem();
        item.setId(id);
        return item;
    }

    default List<VoucherItem> mapVoucherItemIdsToEntities(List<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream().map(this::mapVoucherItemIdToEntity).toList();
    }
}
