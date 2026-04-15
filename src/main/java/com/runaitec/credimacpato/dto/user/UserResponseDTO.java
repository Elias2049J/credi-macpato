package com.runaitec.credimacpato.dto.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.runaitec.credimacpato.dto.user.customer.BusinessCustomerResponseDTO;
import com.runaitec.credimacpato.dto.user.customer.PersonCustomerResponseDTO;
import com.runaitec.credimacpato.dto.user.partner.PartnerResponseDTO;
import com.runaitec.credimacpato.entity.Role;
import com.runaitec.credimacpato.entity.UserState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonCustomerResponseDTO.class, name = "PERSON_CUSTOMER"),
        @JsonSubTypes.Type(value = BusinessCustomerResponseDTO.class, name = "BUSINESS_CUSTOMER"),
        @JsonSubTypes.Type(value = PartnerResponseDTO.class, name = "PARTNER")
})
public abstract class UserResponseDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String username;

    @NotNull
    private Role role;

    @NotNull
    private UserState state;

    @NotNull
    private LocalDateTime createdAt;

    @NotBlank
    private String fullName;
}
