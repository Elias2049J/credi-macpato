package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VoteDTO {
    private Long id;
    @NotNull
    private Long loanRequestId;
    @NotNull
    private Long userId;
    @NotNull
    private Boolean approved;
}
