package com.runaitec.credimacpato.dto.payment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long voucherId;

    @NotEmpty
    private List<Long> paidItemIds;
}
