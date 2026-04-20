package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.FileType;
import com.runaitec.credimacpato.dto.debt.BulkDebtRowRequestDTO;
import com.runaitec.credimacpato.dto.debt.BulkDebtUploadErrorDTO;
import com.runaitec.credimacpato.dto.debt.BulkDebtUploadRequestDTO;
import com.runaitec.credimacpato.dto.debt.BulkDebtUploadResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherItemRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.entity.PaymentState;
import com.runaitec.credimacpato.entity.Stand;
import com.runaitec.credimacpato.entity.Voucher;
import com.runaitec.credimacpato.entity.VoucherItem;
import com.runaitec.credimacpato.entity.Charge;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BillingServiceImpl implements BillingService {
    private static final int SERIAL_SAVE_RETRIES = 3;
    private final FileImportUtil fileImportUtil;
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
    public VoucherResponseDTO findById(Long voucherId) {
        return voucherMapper.toResponseDto(voucherRepository.findById(voucherId).orElseThrow());
    }

    @Override
    public List<VoucherResponseDTO> listByStand(Long standId) {
        return voucherRepository.findAllByStand_Id(standId)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VoucherResponseDTO> listByCustomer(Long customerId) {
        return voucherRepository.findAllByCustomer_Id(customerId)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VoucherResponseDTO> listPendingVouchersByCustomer(Long customerId) {
        return voucherRepository.findAllByCustomer_IdAndState(customerId, PaymentState.PENDING)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VoucherResponseDTO> listPendingVouchersByIssuer(Long partnerId) {
        return voucherRepository.findAllByIssuer_IdAndState(partnerId, PaymentState.PENDING)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VoucherResponseDTO> listVouchersByStandAndIssueDateBetween(Long standId, LocalDate from, LocalDate to) {
        return voucherRepository.findAllByStand_IdAndIssueDateBetween(standId, from, to)
                .stream()
                .map(voucherMapper::toResponseDto)
                .toList();
    }

    @Override
    public BulkDebtUploadResponseDTO importDebtsBulk(MultipartFile file, FileType fileType) {
        if (fileType == null) {
            throw new IllegalArgumentException("fileType is required");
        }

        BulkDebtUploadRequestDTO request = switch (fileType) {
            case CSV -> fileImportUtil.parseCsvToDTO(file);
            case EXCEL -> fileImportUtil.parseExcelToDTO(file);
            default -> throw new IllegalArgumentException("Unsupported file type for debt import: " + fileType);
        };

        return importDebtsBulk(request);
    }

    private BulkDebtUploadResponseDTO importDebtsBulk(BulkDebtUploadRequestDTO request) {
        if (request == null || request.getRows() == null || request.getRows().isEmpty()) {
            throw new IllegalArgumentException("There are no rows to import");
        }

        BulkDebtUploadResponseDTO response = new BulkDebtUploadResponseDTO();
        response.setRequestedCount(request.getRows().size());

        ImportContext ctx = buildImportContext(request.getRows());

        List<Voucher> toPersist = new ArrayList<>();

        int rowIndex = 0;
        for (BulkDebtRowRequestDTO row : request.getRows()) {
            rowIndex++;
            try {
                Voucher voucher = buildVoucherFromRow(ctx, row, rowIndex);
                toPersist.add(voucher);
            } catch (RuntimeException ex) {
                response.getErrors().add(new BulkDebtUploadErrorDTO(rowIndex, ex.getMessage()));
            }
        }

        if (toPersist.isEmpty()) {
            response.setCreatedCount(0);
            return response;
        }

        List<Voucher> saved = saveAllWithSerialRetry(toPersist);
        response.setCreatedCount(saved.size());
        response.setCreatedVouchers(saved.stream().map(voucherMapper::toResponseDto).toList());
        return response;
    }

    private List<Voucher> saveAllWithSerialRetry(List<Voucher> vouchers) {
        DataIntegrityViolationException last = null;

        for (int attempt = 1; attempt <= SERIAL_SAVE_RETRIES; attempt++) {
            try {
                return voucherRepository.saveAll(vouchers);
            } catch (DataIntegrityViolationException ex) {
                last = ex;
                vouchers.forEach(v -> v.setSerialNumber(generateVoucherSerial(v)));
            }
        }

        throw last;
    }

    private Voucher buildVoucherFromRow(ImportContext ctx, BulkDebtRowRequestDTO row, int rowIndex) {
        final String rowMsg = rowPrefix(rowIndex);

        requireNotNull(row, rowMsg + "row is required");

        Long standId = requireNotNull(row.getStandId(), rowMsg + "standId is required");
        Long issuerId = requireNotNull(row.getIssuerId(), rowMsg + "issuerId is required");
        Long customerId = requireNotNull(row.getCustomerId(), rowMsg + "customerId is required");

        Stand stand = ctx.stands.get(standId);
        if (stand == null) {
            throw new IllegalArgumentException(rowMsg + "standId does not exist: " + standId);
        }

        User issuer = ctx.users.get(issuerId);
        if (issuer == null) {
            throw new IllegalArgumentException(rowMsg + "issuerId does not exist: " + issuerId);
        }

        Customer customer = ctx.customers.get(customerId);
        if (customer == null) {
            if (ctx.users.containsKey(customerId)) {
                throw new IllegalArgumentException(rowMsg + "User " + customerId + " is not a Customer");
            }
            throw new IllegalArgumentException(rowMsg + "customerId does not exist: " + customerId);
        }

        BigDecimal igv = Optional.ofNullable(row.getIgv()).orElse(BigDecimal.ZERO);
        if (igv.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(rowMsg + "igv cannot be negative");
        }

        if (row.getVoucherItems() == null || row.getVoucherItems().isEmpty()) {
            throw new IllegalArgumentException(rowMsg + "voucherItems are required");
        }

        VoucherRequestDTO dto = new VoucherRequestDTO();
        dto.setIgv(igv);
        dto.setIssuerId(issuerId);
        dto.setCustomerId(customerId);
        dto.setStandId(standId);
        dto.setVoucherItems(row.getVoucherItems());

        Voucher voucher = voucherMapper.toEntity(dto);

        voucher.setStand(stand);
        voucher.setIssuer(issuer);
        voucher.setCustomer(customer);

        LocalDate issueDate = row.getIssueDate();
        if (issueDate != null) {
            voucher.setIssueDate(issueDate);
        }

        resolveAndValidateItems(ctx, voucher, row.getVoucherItems(), rowIndex);

        voucher.setSerialNumber(generateVoucherSerial(voucher));
        voucher.calculateTotal();
        return voucher;
    }

    private void resolveAndValidateItems(ImportContext ctx, Voucher voucher, List<VoucherItemRequestDTO> itemDtos, int rowIndex) {
        final String rowMsg = rowPrefix(rowIndex);

        List<VoucherItem> items = voucher.getVoucherItems();
        if (items == null || items.isEmpty()) {
            throw new IllegalStateException(rowMsg + "items couldn't be mapped");
        }
        if (items.size() != itemDtos.size()) {
            throw new IllegalStateException(rowMsg + "mapped items count does not match input");
        }

        for (int i = 0; i < items.size(); i++) {
            int itemPosition = i + 1;
            String itemMsg = itemPrefix(rowIndex, itemPosition);

            VoucherItemRequestDTO dto = itemDtos.get(i);
            VoucherItem entity = items.get(i);

            if (dto == null) {
                throw new IllegalArgumentException(itemMsg + "item is null");
            }

            Long chargeId = dto.getChargeReasonId();
            if (chargeId == null) {
                throw new IllegalArgumentException(itemMsg + "chargeReasonId is required");
            }

            Charge charge = ctx.charges.get(chargeId);
            if (charge == null) {
                throw new IllegalArgumentException(itemMsg + "chargeReasonId does not exist: " + chargeId);
            }

            entity.setVoucher(voucher);
            entity.setCharge(charge);

            if (entity.getQuantity() == null || entity.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(itemMsg + "quantity must be > 0");
            }

            if (entity.getUnitValue() == null || entity.getUnitValue().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(itemMsg + "unitValue must be >= 0");
            }

            if (entity.getMeasureUnitType() == null) {
                throw new IllegalArgumentException(itemMsg + "measureUnitType is required");
            }

            entity.calculateTotal();
        }
    }

    private ImportContext buildImportContext(List<BulkDebtRowRequestDTO> rows) {
        Set<Long> standIds = new HashSet<>();
        Set<Long> userIds = new HashSet<>();
        Set<Long> chargeIds = new HashSet<>();

        for (BulkDebtRowRequestDTO row : rows) {
            if (row == null) {
                continue;
            }

            if (row.getStandId() != null) {
                standIds.add(row.getStandId());
            }
            if (row.getIssuerId() != null) {
                userIds.add(row.getIssuerId());
            }
            if (row.getCustomerId() != null) {
                userIds.add(row.getCustomerId());
            }

            if (row.getVoucherItems() != null) {
                for (VoucherItemRequestDTO item : row.getVoucherItems()) {
                    if (item != null && item.getChargeReasonId() != null) {
                        chargeIds.add(item.getChargeReasonId());
                    }
                }
            }
        }

        Map<Long, Stand> stands = standIds.isEmpty()
                ? Map.of()
                : standRepository.findAllById(standIds).stream().collect(Collectors.toMap(Stand::getId, Function.identity()));

        Map<Long, User> users = userIds.isEmpty()
                ? Map.of()
                : userRepository.findAllById(userIds).stream().collect(Collectors.toMap(User::getId, Function.identity()));

        Map<Long, Customer> customers = users.values().stream()
                .filter(Customer.class::isInstance)
                .map(Customer.class::cast)
                .collect(Collectors.toMap(Customer::getId, Function.identity()));

        Map<Long, Charge> charges = chargeIds.isEmpty()
                ? Map.of()
                : chargeRepository.findAllById(chargeIds).stream().collect(Collectors.toMap(Charge::getId, Function.identity()));

        return new ImportContext(stands, users, customers, charges);
    }

    private Customer loadCustomerFromDb(Long customerId) {
        User u = userRepository.findById(customerId).orElseThrow();
        if (!(u instanceof Customer c)) {
            throw new IllegalArgumentException("User " + customerId + " is not a Customer");
        }
        return c;
    }

    private static <T> T requireNotNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private static String rowPrefix(int rowIndex) {
        return "Row " + rowIndex + ": ";
    }

    private static String itemPrefix(int rowIndex, int itemPosition) {
        return "Row " + rowIndex + ", item " + itemPosition + ": ";
    }

    private record ImportContext(
            Map<Long, Stand> stands,
            Map<Long, User> users,
            Map<Long, Customer> customers,
            Map<Long, Charge> charges
    ) {
    }

    private String generateVoucherSerial(Voucher voucher) {
        Integer standNumber = voucher.getStand() == null ? null : voucher.getStand().getNumber();
        if (standNumber == null) {
            throw new IllegalStateException("Stand number is required to generate voucher serial");
        }

        String prefix = buildSeriesPrefix(standNumber);
        Voucher last = voucherRepository.findTopBySerialNumberStartingWithOrderBySerialNumberDesc(prefix + "-");
        long next = parseNextCorrelative(last == null ? null : last.getSerialNumber());
        return prefix + "-" + formatCorrelative(next);
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
}
