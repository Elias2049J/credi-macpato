package com.runaitec.credimacpato.dto.user.association;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociationRequestDTO extends UserRequestDTO {

    @NotBlank
    private String registrationName;

    @NotBlank
    private String address;
}
