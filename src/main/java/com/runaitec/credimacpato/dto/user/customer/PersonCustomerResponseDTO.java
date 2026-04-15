package com.runaitec.credimacpato.dto.user.customer;

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
public class PersonCustomerResponseDTO extends CustomerResponseDTO {

    private String name;

    private String lastname;
}
