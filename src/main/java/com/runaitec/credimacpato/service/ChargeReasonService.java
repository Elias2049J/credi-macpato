package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.chargeReason.ChargeReasonRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeReasonResponseDTO;

import java.util.List;

public interface ChargeReasonService {
    List<ChargeReasonResponseDTO> findAll();
    ChargeReasonResponseDTO findById(Long id);

    ChargeReasonResponseDTO create(ChargeReasonRequestDTO request);
    ChargeReasonResponseDTO update(Long id, ChargeReasonRequestDTO request);

    void delete(Long id);
}
