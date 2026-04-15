package com.runaitec.credimacpato.dto.user.partner;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonPartnerResponseDTO.class, name = "PERSON_PARTNER"),
        @JsonSubTypes.Type(value = BusinessPartnerResponseDTO.class, name = "BUSINESS_PARTNER"),
        @JsonSubTypes.Type(value = AssociationResponseDTO.class, name = "ASSOCIATION")
})
public abstract class PartnerResponseDTO extends UserResponseDTO {

    private BigDecimal moneyBalance;
    private List<Long> emmitedVouchersIds;
}
