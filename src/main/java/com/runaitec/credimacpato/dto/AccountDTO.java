package com.runaitec.credimacpato.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AccountDTO {
    private Long id;
    private Long accountNumber;
    private LocalDateTime openingDate;
    private BigDecimal amount;
    private String accountState;
}
