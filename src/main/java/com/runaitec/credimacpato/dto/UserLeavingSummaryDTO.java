package com.runaitec.credimacpato.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserLeavingSummaryDTO {
    private UserDTO userDTO;
    private AccountDTO accountDTO;
    private List<LoanDTO> loansHistory;
    private List<ProfitDTO> profitsHistory;
}
