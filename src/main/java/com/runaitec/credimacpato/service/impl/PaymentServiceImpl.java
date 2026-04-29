package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.payment.PaymentRequestDTO;
import com.runaitec.credimacpato.dto.payment.PaymentResponseDTO;
import com.runaitec.credimacpato.entity.Payment;
import com.runaitec.credimacpato.entity.PaymentState;
import com.runaitec.credimacpato.entity.Voucher;
import com.runaitec.credimacpato.entity.VoucherItem;
import com.runaitec.credimacpato.entity.user.Customer;
import com.runaitec.credimacpato.mapper.PaymentMapper;
import com.runaitec.credimacpato.repository.PaymentRepository;
import com.runaitec.credimacpato.repository.UserRepository;
import com.runaitec.credimacpato.repository.VoucherItemRepository;
import com.runaitec.credimacpato.repository.VoucherRepository;
import com.runaitec.credimacpato.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final VoucherItemRepository voucherItemRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentResponseDTO payVoucherItems(PaymentRequestDTO request) {
        Payment payment = paymentMapper.toEntity(request);
        List<VoucherItem> paidItems = voucherItemRepository.findAllById(request.getPaidItemIds());
        Voucher voucher = voucherRepository.findById(request.getVoucherId()).orElseThrow();

        payment.setPaidItems(paidItems);
        payment.setAmount(paidItems.stream()
                .map(i -> i.getPayableAmount() == null ? BigDecimal.ZERO : i.getPayableAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        payment.setCode(UUID.randomUUID().toString());
        payment.setVoucher(voucher);
        payment.setCustomer((Customer) userRepository.getReferenceById(request.getCustomerId()));

        payment.getPaidItems().forEach(i -> {
            i.setState(PaymentState.PAID);
            i.setPayment(payment);
        });

        updateVoucherState(voucher);
        voucher.getPayments().add(payment);

        Voucher savedVoucher = voucherRepository.save(voucher);

        Payment savedPayment = savedVoucher.getPayments().stream()
                .filter(p -> p.getCode() != null && p.getCode().equals(payment.getCode()))
                .findFirst()
                .orElse(payment);

        return paymentMapper.toResponseDto(savedPayment);
    }

    private void updateVoucherState(Voucher voucher) {
        if (voucher.getVoucherItems()
                .stream()
                .allMatch(i -> i.getState() != null && i.getState().isPaid())) {
            voucher.setState(PaymentState.PAID);
        } else if (voucher.getVoucherItems()
                .stream()
                .anyMatch(i -> i.getState() != null && i.getState().isPaid())){
            voucher.setState(PaymentState.PARTIALLY_PAID);
        } else voucher.setState(PaymentState.PENDING);
    }

    @Override
    public PaymentResponseDTO findById(Long paymentId) {
        Payment p = paymentRepository.findById(paymentId).orElseThrow();
        p.setPaidItems(voucherItemRepository.findAllByPayment_Id(paymentId));
        return paymentMapper.toResponseDto(p);
    }

    @Override
    public List<PaymentResponseDTO> listByCustomer(Long customerId) {
        return paymentRepository.findAllByCustomer_Id(customerId)
                .stream()
                .map(paymentMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDTO> listByVoucher(Long voucherId) {
        return paymentRepository.findAllByVoucher_Id(voucherId)
                .stream()
                .map(paymentMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDTO> listPaymentsByStandAndDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to) {
        return paymentRepository.findAllByVoucher_Stand_IdAndDateTimeBetween(standId, from, to)
                .stream()
                .map(paymentMapper::toResponseDto)
                .toList();
    }
}
