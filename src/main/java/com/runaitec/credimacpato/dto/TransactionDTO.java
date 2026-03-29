package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TransactionDTO {
    private Long id;
    @NotNull
    private LocalDateTime date;
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
    @NotNull
    private AccountDTO originAccount;
    @NotNull
    private AccountDTO destinationAccount;
}
