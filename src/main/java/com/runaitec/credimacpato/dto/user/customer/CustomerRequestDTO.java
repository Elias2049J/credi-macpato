package com.runaitec.credimacpato.dto.user.customer;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public abstract class CustomerRequestDTO extends UserRequestDTO {
    @NotNull
    private Long associationId;
}