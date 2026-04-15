package com.runaitec.credimacpato.dto.user.partner;

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
@ToString(callSuper = true)
public class AssociationResponseDTO extends BusinessPartnerResponseDTO {
    private List<Long> partnerIds;
}
