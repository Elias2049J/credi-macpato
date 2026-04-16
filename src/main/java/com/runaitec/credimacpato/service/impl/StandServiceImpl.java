package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.stand.StandRequestDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.entity.Stand;
import com.runaitec.credimacpato.mapper.StandMapper;
import com.runaitec.credimacpato.repository.StandRepository;
import com.runaitec.credimacpato.mapper.RestMapper;
import com.runaitec.credimacpato.service.StandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class StandServiceImpl implements StandService {
    private final StandRepository standRepository;
    private final StandMapper standMapper;
    @Override
    public JpaRepository<Stand, Long> repository() {
        return standRepository;
    }

    @Override
    public RestMapper<Stand, StandResponseDTO, StandRequestDTO> mapper() {
        return standMapper;
    }

    @Override
    public StandResponseDTO update(Long aLong, StandRequestDTO request) {
        return null;
    }

    @Override
    public StandResponseDTO changeOwner(Long standId, Long newOwnerId) {
        return null;
    }

    @Override
    public List<StandResponseDTO> listStandsByOwner(Long ownerId) {
        return List.of();
    }
}
