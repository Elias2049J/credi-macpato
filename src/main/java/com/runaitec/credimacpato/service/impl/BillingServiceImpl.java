package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.entity.MeasureUnitType;
import com.runaitec.credimacpato.entity.PaymentState;
import com.runaitec.credimacpato.entity.Voucher;
import com.runaitec.credimacpato.entity.user.Customer;
import com.runaitec.credimacpato.entity.user.User;
import com.runaitec.credimacpato.mapper.VoucherMapper;
import com.runaitec.credimacpato.repository.ChargeRepository;
import com.runaitec.credimacpato.repository.StandRepository;
import com.runaitec.credimacpato.repository.UserRepository;
import com.runaitec.credimacpato.repository.VoucherRepository;
import com.runaitec.credimacpato.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BillingServiceImpl implements BillingService {
    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;
    private final UserRepository userRepository;
    private final StandRepository standRepository;
    private final ChargeRepository chargeRepository;

    @Override
    public VoucherResponseDTO issue(VoucherRequestDTO request) {
        Voucher voucher = voucherMapper.toEntity(request);
        voucher.getVoucherItems().forEach(i -> i.setCharge(
                chargeRepository.getReferenceById(i.getCharge().getId())
        ));

        voucher.setCustomer(loadCustomerFromDb(request.getCustomerId()));
        voucher.setIssuer(userRepository.getReferenceById(request.getIssuerId()));
        voucher.setStand(standRepository.getReferenceById(request.getStandId()));
        voucher.setSerialNumber(generateVoucherSerial(voucher));
        voucher.calculateTotal();
        return voucherMapper.toResponseDto(voucherRepository.save(voucher));
    }

    @Override
    @Transactional(readOnly = true)
    public VoucherResponseDTO findById(Long voucherId) {
        return voucherMapper.toResponseDto(voucherRepository.findById(voucherId).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherResponseDTO> listByStand(Long standId) {
        return voucherRepository.findAllByStand_Id(standId)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherResponseDTO> listByCustomer(Long customerId) {
        return voucherRepository.findAllByCustomer_Id(customerId)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherResponseDTO> listPendingVouchersByCustomer(Long customerId) {
        return voucherRepository.findAllByCustomer_IdAndState(customerId, PaymentState.PENDING)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherResponseDTO> listPendingVouchersByIssuer(Long partnerId) {
        return voucherRepository.findAllByIssuer_IdAndState(partnerId, PaymentState.PENDING)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherResponseDTO> listVouchersByStandAndIssueDateBetween(Long standId, LocalDate from, LocalDate to) {
        return voucherRepository.findAllByStand_IdAndIssueDateBetween(standId, from, to)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    public String generateVoucherSerial(Voucher voucher) {
        Integer standNumber = voucher.getStand() == null ? null : voucher.getStand().getNumber();
        if (standNumber == null) {
            throw new IllegalStateException("Stand number is required to generate voucher serial");
        }

        String prefix = buildSeriesPrefix(standNumber);
        Voucher last = voucherRepository.findTopBySerialNumberStartingWithOrderBySerialNumberDesc(prefix + "-");
        long next = parseNextCorrelative(last == null ? null : last.getSerialNumber());
        return prefix + "-" + formatCorrelative(next);
    }

    @Override
    public MeasureUnitType[] listUnits() {
        return MeasureUnitType.values();
    }

    private String buildSeriesPrefix(int standNumber) {
        return "F" + String.format("%03d", standNumber);
    }

    private long parseNextCorrelative(String lastSerial) {
        if (lastSerial == null) {
            return 1L;
        }
        int dashIndex = lastSerial.lastIndexOf('-');
        if (dashIndex < 0 || dashIndex + 1 >= lastSerial.length()) {
            return 1L;
        }
        try {
            return Long.parseLong(lastSerial.substring(dashIndex + 1)) + 1L;
        } catch (NumberFormatException ignored) {
            return 1L;
        }
    }

    private String formatCorrelative(long next) {
        return String.format("%08d", next);
    }

    private Customer loadCustomerFromDb(Long customerId) {
        User u = userRepository.findById(customerId).orElseThrow();
        if (!(u instanceof Customer c)) {
            throw new IllegalArgumentException("User " + customerId + " is not a Customer");
        }
        return c;
    }
}
