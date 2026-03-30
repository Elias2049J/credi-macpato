package com.runaitec.credimacpato.mapper;

import com.runaitec.credimacpato.dto.TransactionDTO;
import com.runaitec.credimacpato.entity.Transaction;
import com.runaitec.credimacpato.entity.Contribution;
import com.runaitec.credimacpato.entity.Payment;
import com.runaitec.credimacpato.entity.LoanDisburse;
import com.runaitec.credimacpato.dto.ContributionDTO;
import com.runaitec.credimacpato.dto.PaymentDTO;
import com.runaitec.credimacpato.dto.LoanDisburseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface TransactionMapper {
    TransactionDTO toDto(Transaction entity);
    Transaction toEntity(TransactionDTO dto);

    ContributionDTO toDto(Contribution entity);
    Contribution toEntity(ContributionDTO dto);

    PaymentDTO toDto(Payment entity);
    Payment toEntity(PaymentDTO dto);

    LoanDisburseDTO toDto(LoanDisburse entity);
    LoanDisburse toEntity(LoanDisburseDTO dto);

    default TransactionDTO toDtoDispatch(Transaction entity) {
        return switch (entity) {
            case Contribution c -> toDto(c);
            case Payment p -> toDto(p);
            case LoanDisburse l -> toDto(l);
            default -> throw new IllegalArgumentException("Transaction type not supported: " + entity.getClass().getSimpleName());
        };
    }

    default Transaction toEntityDispatch(TransactionDTO dto) {
        return switch (dto) {
            case ContributionDTO c -> toEntity(c);
            case PaymentDTO p -> toEntity(p);
            case LoanDisburseDTO l -> toEntity(l);
            default -> throw new IllegalArgumentException("TransactionDTO type not supported: " + dto.getClass().getSimpleName());
        };
    }
}
