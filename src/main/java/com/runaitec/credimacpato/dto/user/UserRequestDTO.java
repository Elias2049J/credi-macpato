package com.runaitec.credimacpato.dto.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.runaitec.credimacpato.dto.user.association.AssociationRequestDTO;
import com.runaitec.credimacpato.dto.user.customer.BusinessCustomerRequestDTO;
import com.runaitec.credimacpato.dto.user.customer.PersonCustomerRequestDTO;
import com.runaitec.credimacpato.dto.user.partner.*;
import com.runaitec.credimacpato.entity.Role;
import com.runaitec.credimacpato.entity.UserState;
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
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonCustomerRequestDTO.class, name = "PERSON_CUSTOMER"),
        @JsonSubTypes.Type(value = BusinessCustomerRequestDTO.class, name = "BUSINESS_CUSTOMER"),
        @JsonSubTypes.Type(value = AssociationRequestDTO.class, name = "ASSOCIATION"),
        @JsonSubTypes.Type(value = BusinessVendorRequestDTO.class, name = "BUSINESS_PARTNER"),
        @JsonSubTypes.Type(value = PersonVendorRequestDTO.class, name = "PERSON_PARTNER")
})
public abstract class UserRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    private UserState state;
}
