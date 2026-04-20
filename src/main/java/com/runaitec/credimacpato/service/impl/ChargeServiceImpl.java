package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.chargeReason.ChargeRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeResponseDTO;
import com.runaitec.credimacpato.entity.Charge;
import com.runaitec.credimacpato.entity.Stand;
import com.runaitec.credimacpato.mapper.ChargeMapper;
import com.runaitec.credimacpato.repository.ChargeRepository;
import com.runaitec.credimacpato.repository.StandRepository;
import com.runaitec.credimacpato.service.ChargeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {
    private final StandRepository standRepository;
    private final ChargeRepository chargeRepository;
    private final ChargeMapper chargeMapper;

    @Override
    public JpaRepository<Charge, Long> repository() {
        return chargeRepository;
    }

    @Override
    public ChargeMapper mapper() {
        return chargeMapper;
    }

    @Override
    public List<ChargeResponseDTO> findAllByStand(Long standId) {
        return chargeRepository.findAllByStand_Id(standId)
                .stream()
                .map(chargeMapper::toResponse)
                .toList();
    }

    @Override
    public ChargeResponseDTO update(Long id, ChargeRequestDTO request) {
        Charge existing = chargeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Charge not found"));

        Charge mapped = chargeMapper.toEntity(request);
        existing.setDescription(mapped.getDescription());

        if (request.getStandId() != null) {
            Stand stand = standRepository.getReferenceById(request.getStandId());
            existing.setStand(stand);
        }

        return chargeMapper.toResponse(chargeRepository.save(existing));
    }
}
