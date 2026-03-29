package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContributionDTO extends TransactionDTO {
    @NotNull
    private Boolean mandatory;
    @NotNull
    private String recurrency;
}
