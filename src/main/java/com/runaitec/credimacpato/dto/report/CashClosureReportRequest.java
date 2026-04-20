package com.runaitec.credimacpato.dto.report;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CashClosureReportRequest {
    @NotNull
    private LocalDate today;

    @NotNull
    private Long ownerId;
}

