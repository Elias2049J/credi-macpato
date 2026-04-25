package com.runaitec.credimacpato.dto.user.vendor;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString(callSuper = true)
public abstract class VendorRequestDTO extends UserRequestDTO {
    @NotNull
    private Long associationId;
}

