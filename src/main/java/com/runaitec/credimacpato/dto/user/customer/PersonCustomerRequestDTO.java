package com.runaitec.credimacpato.dto.user.customer;

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
public class PersonCustomerRequestDTO extends CustomerRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;
}

