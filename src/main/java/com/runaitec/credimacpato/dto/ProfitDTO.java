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
public class ProfitDTO {
    private Long id;
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
    @NotNull
    private UserDTO user;
    private LocalDateTime localDateTime;
    private BigDecimal totalContribution;
    private BigDecimal proportion;
}
