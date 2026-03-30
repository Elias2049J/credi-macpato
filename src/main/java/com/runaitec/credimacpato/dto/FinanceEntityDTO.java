package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FinanceEntityDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private java.time.LocalDateTime creationDateTime;
    @NotNull
    private AccountDTO account;
    @NotNull
    private BoardOfDirectorsDTO boardOfDirectors;
}
