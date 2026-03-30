package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.PaymentDTO;
import com.runaitec.credimacpato.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface PaymentMapper {
    PaymentDTO toDto(Payment entity);
    Payment toEntity(PaymentDTO dto);
}
