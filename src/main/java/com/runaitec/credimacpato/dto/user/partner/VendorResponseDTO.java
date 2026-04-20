package com.runaitec.credimacpato.dto.user.partner;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class VendorResponseDTO extends UserResponseDTO {

    private BigDecimal moneyBalance;
}
