package com.runaitec.credimacpato.dto.chargeReason;

import jakarta.validation.constraints.NotBlank;
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
public class ChargeReasonRequestDTO {

    @NotBlank
    private String name;

    private String description;
}

