package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.stand.StandRequestDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;

import java.util.List;

public interface StandService {
    List<StandResponseDTO> findAll();
    StandResponseDTO findById(Long id);

    StandResponseDTO create(StandRequestDTO request);
    StandResponseDTO update(Long id, StandRequestDTO request);
    void delete(Long id);

    StandResponseDTO changeOwner(Long standId, Long newOwnerId);
    List<StandResponseDTO> listStandsByOwner(Long ownerId);
}
