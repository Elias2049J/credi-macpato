package com.runaitec.credimacpato.dto.user.vendor;

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
public class BusinessVendorRequestDTO extends VendorRequestDTO {

    @NotBlank
    private String registrationName;

    @NotBlank
    private String address;
}
