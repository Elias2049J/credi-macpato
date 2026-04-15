package com.runaitec.credimacpato.dto.user.partner;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.runaitec.credimacpato.dto.user.UserRequestDTO;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonPartnerRequestDTO.class, name = "PERSON_PARTNER"),
        @JsonSubTypes.Type(value = BusinessPartnerRequestDTO.class, name = "BUSINESS_PARTNER"),
        @JsonSubTypes.Type(value = AssociationRequestDTO.class, name = "ASSOCIATION")
})
public abstract class PartnerRequestDTO extends UserRequestDTO {
}

