package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.entity.Payment;
import com.runaitec.credimacpato.entity.Voucher;
import com.runaitec.credimacpato.entity.VoucherItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VoucherItemMapper.class})
public interface VoucherMapper {

    @Mapping(source = "igv_amount", target = "igvAmount")
    @Mapping(source = "issuer.id", target = "issuerId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "stand.id", target = "standId")
    @Mapping(source = "payments", target = "paymentIds")
    VoucherResponseDTO toResponseDto(Voucher entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "issueDateTime", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "igv_amount", ignore = true)
    @Mapping(target = "lineExtensionAmount", ignore = true)
    @Mapping(target = "payableAmount", ignore = true)
    @Mapping(target = "issuer", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(source = "standId", target = "stand.id")
    @Mapping(target = "payments", ignore = true)
    Voucher toEntity(VoucherRequestDTO dto);

    @AfterMapping
    default void linkItems(@MappingTarget Voucher voucher) {
        List<VoucherItem> items = voucher.getVoucherItems();
        if (items == null || items.isEmpty()) {
            return;
        }
        for (VoucherItem item : items) {
            item.setVoucher(voucher);
        }
    }

    default Long mapPaymentToId(Payment payment) {
        return payment == null ? null : payment.getId();
    }

    default Payment mapPaymentIdToEntity(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }

    default List<Payment> mapPaymentIdsToEntities(List<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream().map(this::mapPaymentIdToEntity).toList();
    }
}
