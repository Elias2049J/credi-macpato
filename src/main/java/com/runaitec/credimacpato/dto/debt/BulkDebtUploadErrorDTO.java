package com.runaitec.credimacpato.dto.debt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BulkDebtUploadErrorDTO {

    private Integer rowIndex;

    private String message;
}

