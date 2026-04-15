package com.runaitec.credimacpato.dto.user.partner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PersonPartnerRequestDTO extends PartnerRequestDTO {

    @NotNull
    private Long associationId;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;
}
