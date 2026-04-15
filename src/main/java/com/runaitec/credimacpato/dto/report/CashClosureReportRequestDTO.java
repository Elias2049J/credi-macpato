package com.runaitec.credimacpato.dto.report;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CashClosureReportRequestDTO {

    @NotNull
    private Long standId;

    @NotNull
    private LocalDateTime from;

    @NotNull
    private LocalDateTime to;

    @NotNull
    private Long vendorId;
}

