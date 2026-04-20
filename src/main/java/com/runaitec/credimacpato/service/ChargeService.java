package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.chargeReason.ChargeRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeResponseDTO;
import com.runaitec.credimacpato.entity.Charge;

import java.util.List;

public interface ChargeService extends CrudService<ChargeResponseDTO, ChargeRequestDTO, Long, Charge> {
    List<ChargeResponseDTO> findAllByStand(Long standId);
}
