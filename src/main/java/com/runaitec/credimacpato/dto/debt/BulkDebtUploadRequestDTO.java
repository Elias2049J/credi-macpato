package com.runaitec.credimacpato.dto.debt;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
public class BulkDebtUploadRequestDTO {

    @NotEmpty
    @Valid
    private List<BulkDebtRowRequestDTO> rows;
}
