package com.runaitec.credimacpato.dto.user.partner;

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
public class PersonVendorResponseDTO extends VendorResponseDTO {

    private Long associationId;

    private String name;

    private String lastname;
}
