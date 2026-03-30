package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class LoanDTO {
    private Long id;
    @NotNull
    private LocalDateTime dueDate;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    @Positive
    private Integer termMonths;
    @NotNull
    @PositiveOrZero
    private BigDecimal interestRate;
    @NotNull
    private List<PaymentDTO> payments;
    @NotNull
    private UserDTO user;
}
