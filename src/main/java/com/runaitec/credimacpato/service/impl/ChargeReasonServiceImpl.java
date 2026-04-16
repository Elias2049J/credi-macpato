package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.chargeReason.ChargeRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeResponseDTO;
import com.runaitec.credimacpato.entity.Charge;
import com.runaitec.credimacpato.mapper.ChargeMapper;
import com.runaitec.credimacpato.repository.ChargeRepository;
import com.runaitec.credimacpato.service.ChargeReasonService;
import com.runaitec.credimacpato.mapper.RestMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChargeReasonServiceImpl implements ChargeReasonService {
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
    public ChargeResponseDTO update(Long aLong, ChargeRequestDTO request) {
        if (!chargeRepository.existsById(aLong)) {
            throw new EntityNotFoundException("Charge not found");
        }
        return chargeMapper.to;
    }
}
