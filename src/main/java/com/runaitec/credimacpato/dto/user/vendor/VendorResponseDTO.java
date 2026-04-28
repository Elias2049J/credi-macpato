package com.runaitec.credimacpato.dto.user.vendor;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class VendorResponseDTO extends UserResponseDTO {
    private Long associationId;
    private BigDecimal moneyBalance;
}
