package com.runaitec.credimacpato.dto.chargeReason;

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
public class ChargeResponseDTO {

    private Long id;

    private String name;

    private String description;
}

