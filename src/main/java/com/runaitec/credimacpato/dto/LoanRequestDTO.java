package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class LoanRequestDTO {
    private Long id;
    @NotNull
    private String status;
    @NotNull
    @NotEmpty
    private List<VoteDTO> votes;
}
