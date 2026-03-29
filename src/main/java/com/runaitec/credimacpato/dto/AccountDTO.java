package com.runaitec.credimacpato.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AccountDTO {
    private Long id;
    @NotNull
    private Long accountNumber;
    @NotNull
    private LocalDateTime openingDate;
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
    @NotNull
    private String accountState;
}
