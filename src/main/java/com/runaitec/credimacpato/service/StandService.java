package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.stand.StandRequestDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.entity.Stand;

import java.util.List;

public interface StandService extends CrudService<StandResponseDTO, StandRequestDTO, Long, Stand>{
    StandResponseDTO changeOwner(Long standId, Long newOwnerId);
    List<StandResponseDTO> listStandsByOwner(Long ownerId);
}
