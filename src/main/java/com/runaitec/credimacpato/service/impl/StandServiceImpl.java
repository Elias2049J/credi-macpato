package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.stand.StandRequestDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.entity.Stand;
import com.runaitec.credimacpato.entity.user.User;
import com.runaitec.credimacpato.entity.user.Vendor;
import com.runaitec.credimacpato.mapper.StandMapper;
import com.runaitec.credimacpato.repository.StandRepository;
import com.runaitec.credimacpato.mapper.RestMapper;
import com.runaitec.credimacpato.repository.UserRepository;
import com.runaitec.credimacpato.service.StandService;
import jakarta.persistence.EntityNotFoundException;
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
    private final UserRepository userRepository;

    @Override
    public JpaRepository<Stand, Long> repository() {
        return standRepository;
    }

    @Override
    public RestMapper<Stand, StandResponseDTO, StandRequestDTO> mapper() {
        return standMapper;
    }

    @Override
    public StandResponseDTO create(StandRequestDTO request) {
        Stand stand = standMapper.toEntity(request);
        Vendor vendor = (Vendor) userRepository.findById(request.getOwnerId()).orElseThrow();
        stand.setOwner(vendor);
        stand.setNumber(generateNextStandNumber(vendor.getId()));
        return standMapper.toResponse(standRepository.save(stand));
    }

    private int generateNextStandNumber(Long partnerId) {
        Stand last = standRepository.findTopByOwner_IdOrderByNumberDesc(partnerId);
        return last == null ? 1 : last.getNumber() + 1;
    }

    @Override
    public StandResponseDTO update(Long aLong, StandRequestDTO request) {
        if(!standRepository.existsById(aLong)) {
            throw new EntityNotFoundException("Stand not found");
        }
        Stand toUpdate = standMapper.toEntity(request);
        toUpdate.setId(aLong);
        return standMapper.toResponse(standRepository.save(toUpdate));
    }

    @Override
    public StandResponseDTO changeOwner(Long standId, Long newOwnerId) {
        return null;
    }

    @Override
    public List<StandResponseDTO> listStandsByOwner(Long ownerId) {
        return standRepository.findAllByOwner_Id(ownerId)
                .stream()
                .map(standMapper::toResponse)
                .toList();
    }
}
