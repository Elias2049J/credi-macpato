package com.runaitec.credimacpato.dto.user.association;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
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
public class AssociationResponseDTO extends UserResponseDTO {

    private String registrationName;

    private String address;
}
