package com.runaitec.credimacpato.dto.user.partner;

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
@ToString(callSuper = true)
public class BusinessPartnerRequestDTO extends PartnerRequestDTO {

    @NotBlank
    private String registrationName;

    @NotBlank
    private String address;
}
