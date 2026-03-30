package com.runaitec.credimacpato.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReportDTO {
    private Long id;
    @NotNull
    private LocalDateTime creationDate;
}
