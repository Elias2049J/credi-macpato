package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.chargeReason.ChargeRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeResponseDTO;
import com.runaitec.credimacpato.entity.Charge;

public interface ChargeReasonService extends CrudService<ChargeResponseDTO, ChargeRequestDTO, Long, Charge>{
}
