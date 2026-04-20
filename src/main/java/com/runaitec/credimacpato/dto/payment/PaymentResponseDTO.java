package com.runaitec.credimacpato.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentResponseDTO {
    private Long id;

    private String code;

    private LocalDateTime dateTime;

    private BigDecimal amount;

    private Long customerId;

    private Long voucherId;

    private List<Long> paidItemIds;
}
