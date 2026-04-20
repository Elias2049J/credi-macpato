package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.report.CashClosureReportRequest;
import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.CustomerDebtsReportRequest;
import com.runaitec.credimacpato.dto.report.PartnerDebtsReport;
import com.runaitec.credimacpato.dto.report.VouchersByStandDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherItemResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.entity.PaymentState;
import com.runaitec.credimacpato.entity.Stand;
import com.runaitec.credimacpato.mapper.StandMapper;
import com.runaitec.credimacpato.mapper.VoucherMapper;
import com.runaitec.credimacpato.repository.PaymentRepository;
import com.runaitec.credimacpato.repository.StandRepository;
import com.runaitec.credimacpato.repository.VoucherRepository;
import com.runaitec.credimacpato.service.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {
    private final VoucherRepository voucherRepository;
    private final PaymentRepository paymentRepository;
    private final StandRepository standRepository;
    private final StandMapper standMapper;
    private final VoucherMapper voucherMapper;

    @Override
    public CashClosureReport generateCashClosureReport(CashClosureReportRequest request) {
        List<Stand> stands = findStandsByOwnerId(request.getOwnerId());

        List<VouchersByStandDTO> vouchersByStand = buildVouchersByStand(stands, request.getOwnerId(), request.getToday());

        CashClosureReport report = new CashClosureReport();
        report.setVendorId(request.getOwnerId());
        report.setTotalSalesToday(sumTotalSales(vouchersByStand));
        report.setTotalDebtToday(sumTotalDebt(vouchersByStand));
        report.setTotalVouchersCountToday(countTodayVouchers(request.getOwnerId(), request.getToday()));
        report.setVouchersByStand(vouchersByStand);
        report.setCreatedAt(LocalDateTime.now());
        return report;
    }

    @Override
    public PartnerDebtsReport generatePartnerDebtsReport(CustomerDebtsReportRequest request) {
        return null;
    }

    private List<Stand> findStandsByOwnerId(Long ownerId) {
        return standRepository.findAllByOwner_Id(ownerId);
    }

    private long countTodayVouchers(Long ownerId, LocalDate today) {
        return voucherRepository.countAllByIssuer_IdAndIssueDate(ownerId, today);
    }

    private List<VouchersByStandDTO> buildVouchersByStand(List<Stand> stands, Long ownerId, LocalDate today) {
        List<VouchersByStandDTO> result = new ArrayList<>();
        for (Stand stand : stands) {
            result.add(buildVoucherByStand(stand, ownerId, today));
        }
        return result;
    }

    private VouchersByStandDTO buildVoucherByStand(Stand stand, Long ownerId, LocalDate today) {
        StandResponseDTO standResponse = standMapper.toResponse(stand);

        LocalDateTime from = today.atStartOfDay();
        LocalDateTime to = today.plusDays(1).atStartOfDay();

        BigDecimal totalSales = calculateTotalSalesByStandAndPaymentDateTimeBetween(stand.getId(), from, to, today);

        List<VoucherResponseDTO> todayStandVouchers = findStandVouchers(stand.getId(), ownerId, today);
        BigDecimal totalDebt = calculateTotalDebtForIssuedItemsToday(todayStandVouchers);

        return new VouchersByStandDTO(
                (long) todayStandVouchers.size(),
                totalSales,
                totalDebt,
                standResponse,
                todayStandVouchers
        );
    }

    private List<VoucherResponseDTO> findStandVouchers(Long standId, Long issuerId, LocalDate today) {
        return voucherRepository.findAllByStand_IdAndIssuer_IdAndIssueDate(standId, issuerId, today)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    private BigDecimal calculateTotalSalesByStandAndPaymentDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to, LocalDate today) {
        return paymentRepository.findAllByVoucher_Stand_IdAndDateTimeBetween(standId, from, to)
                .stream()
                .flatMap(p -> p.getPaidItems() == null ? Stream.empty() : p.getPaidItems().stream())
                .filter(i -> i.getVoucher() != null && i.getVoucher().getIssueDate() != null && !i.getVoucher().getIssueDate().isAfter(today))
                .map(i -> i.getPayableAmount() == null ? BigDecimal.ZERO : i.getPayableAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalDebtForIssuedItemsToday(List<VoucherResponseDTO> todayVouchers) {
        return todayVouchers.stream()
                .flatMap(v -> v.getVoucherItems() == null ? Stream.empty() : v.getVoucherItems().stream())
                .filter(i -> i.getState() == PaymentState.PENDING)
                .map(VoucherItemResponseDTO::getPayableAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumTotalSales(List<VouchersByStandDTO> vouchersByStand) {
        return vouchersByStand.stream()
                .map(VouchersByStandDTO::getTotalSales)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumTotalDebt(List<VouchersByStandDTO> vouchersByStand) {
        return vouchersByStand.stream()
                .map(VouchersByStandDTO::getTotalDebt)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
