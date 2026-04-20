package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.debt.BulkDebtRowRequestDTO;
import com.runaitec.credimacpato.dto.debt.BulkDebtUploadRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherItemRequestDTO;
import com.runaitec.credimacpato.entity.MeasureUnitType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class FileImportUtil{

    private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";

    private static final String CONTENT_TYPE_CSV = "text/csv";

    private static final List<String> REQUIRED_HEADERS = List.of(
            "standid",
            "issuerid",
            "customerid",
            "igv",
            "quantity",
            "measureunittype",
            "chargereasonid",
            "unitvalue"
    );

    public BulkDebtUploadRequestDTO parseExcelToDTO(MultipartFile file) {
        validateExcelFile(file);

        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getNumberOfSheets() == 0 ? null : workbook.getSheetAt(0);
            if (sheet == null) {
                throw new IllegalArgumentException("El Excel no contiene hojas");
            }

            Iterator<Row> rows = sheet.rowIterator();
            if (!rows.hasNext()) {
                throw new IllegalArgumentException("El Excel no contiene filas");
            }

            Map<String, Integer> cols = parseHeaderRowToIndex(rows.next());
            Map<RowKey, BulkDebtRowAccumulator> groups = new LinkedHashMap<>();

            while (rows.hasNext()) {
                Row row = rows.next();
                if (isRowEmpty(row)) {
                    continue;
                }
                parseDataRow(groups, new ExcelRowReader(row, cols));
            }

            return toUploadDto(groups);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo leer el Excel: " + e.getMessage(), e);
        }
    }

    public BulkDebtUploadRequestDTO parseCsvToDTO(MultipartFile file) {
        validateCsvFile(file);

        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT
                     .builder()
                     .setTrim(true)
                     .setIgnoreEmptyLines(true)
                     .setSkipHeaderRecord(true)
                     .setHeader()
                     .build()
                     .parse(reader)) {

            Map<String, Integer> cols = normalizeCsvHeader(parser.getHeaderMap());
            validateRequiredHeaders(cols);

            Map<RowKey, BulkDebtRowAccumulator> groups = new LinkedHashMap<>();

            for (CSVRecord record : parser) {
                int rowNumber = (int) record.getRecordNumber() + 1;
                if (isCsvRecordBlank(record)) {
                    continue;
                }
                parseDataRow(groups, new CsvRecordReader(record, cols, rowNumber));
            }

            return toUploadDto(groups);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo leer el CSV: " + e.getMessage(), e);
        }
    }

    private void parseDataRow(Map<RowKey, BulkDebtRowAccumulator> groups, RowReader r) {
        processRow(groups,
                r.rowNumber(),
                r.readLong("standId"),
                r.readLong("issuerId"),
                r.readLong("customerId"),
                r.readLocalDate("issueDate"),
                r.readBigDecimal("igv"),
                r.readBigDecimal("quantity"),
                r.readMeasureUnitType("measureUnitType"),
                r.readLong("chargeReasonId"),
                r.readBigDecimal("unitValue")
        );
    }

    private void requireMeasureUnitType(int rowNumber, MeasureUnitType value) {
        if (value == null) {
            throw new IllegalArgumentException("Fila " + rowNumber + ": 'measureUnitType' es requerido");
        }
    }

    private void processRow(Map<RowKey, BulkDebtRowAccumulator> groups,
                            int rowNumber,
                            Long standId,
                            Long issuerId,
                            Long customerId,
                            LocalDate issueDate,
                            BigDecimal igv,
                            BigDecimal quantity,
                            MeasureUnitType measureUnitType,
                            Long chargeReasonId,
                            BigDecimal unitValue) {

        requireMeasureUnitType(rowNumber, measureUnitType);

        VoucherItemRequestDTO item = new VoucherItemRequestDTO(quantity, measureUnitType, chargeReasonId, unitValue);
        RowKey key = new RowKey(standId, issuerId, customerId, issueDate, igv);

        groups.computeIfAbsent(key, k -> new BulkDebtRowAccumulator(standId, issuerId, customerId, issueDate, igv))
                .items
                .add(item);
    }

    private BulkDebtUploadRequestDTO toUploadDto(Map<RowKey, BulkDebtRowAccumulator> groups) {
        List<BulkDebtRowRequestDTO> dtoRows = groups.values().stream().map(BulkDebtRowAccumulator::toDto).toList();
        if (dtoRows.isEmpty()) {
            throw new IllegalArgumentException("El archivo no contiene deudas para importar");
        }
        return new BulkDebtUploadRequestDTO(dtoRows);
    }

    private Map<String, Integer> normalizeCsvHeader(Map<String, Integer> headerMap) {
        if (headerMap == null || headerMap.isEmpty()) {
            throw new IllegalArgumentException("El CSV no contiene cabecera");
        }
        Map<String, Integer> cols = new HashMap<>();
        headerMap.forEach((k, v) -> {
            if (k != null && !k.isBlank()) {
                cols.put(normalizeHeaderKey(k), v);
            }
        });
        return cols;
    }

    private boolean isCsvRecordBlank(CSVRecord record) {
        if (record == null) {
            return true;
        }
        for (String v : record) {
            if (v != null && !v.isBlank()) {
                return false;
            }
        }
        return true;
    }

    private Map<String, Integer> parseHeaderRowToIndex(Row header) {
        if (header == null) {
            throw new IllegalArgumentException("El archivo no contiene cabecera");
        }

        Map<String, Integer> cols = new HashMap<>();
        short last = header.getLastCellNum();
        for (int i = 0; i < last; i++) {
            String name = cellToString(header.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
            if (name == null || name.isBlank()) {
                continue;
            }
            cols.put(normalizeHeaderKey(name), i);
        }
        validateRequiredHeaders(cols);
        return cols;
    }

    private void validateRequiredHeaders(Map<String, Integer> cols) {
        for (String required : REQUIRED_HEADERS) {
            if (!cols.containsKey(required)) {
                throw new IllegalArgumentException("Falta la columna requerida en cabecera: " + required);
            }
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        short last = row.getLastCellNum();
        for (int i = Math.max(row.getFirstCellNum(), 0); i < last; i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            String s = cellToString(cell);
            if (s != null && !s.isBlank()) {
                return false;
            }
        }
        return true;
    }

    private static String cellToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case BLANK -> null;
            case NUMERIC -> cell.toString();
            case FORMULA -> {
                try {
                    yield cell.getStringCellValue();
                } catch (Exception ignored) {
                    yield cell.toString();
                }
            }
            default -> cell.toString();
        };
    }

    private String normalizeHeaderKey(String key) {
        return key == null ? "" : key.replaceAll("\\s+", "").trim().toLowerCase(Locale.ROOT);
    }

    private void validateExcelFile(MultipartFile file) {
        validateFilePresent(file);

        String lowerName = Optional.ofNullable(file.getOriginalFilename()).orElse("").toLowerCase(Locale.ROOT);
        String contentType = file.getContentType();

        boolean isXlsx = lowerName.endsWith(".xlsx") || CONTENT_TYPE_XLSX.equalsIgnoreCase(contentType);
        boolean isXls = lowerName.endsWith(".xls") || CONTENT_TYPE_XLS.equalsIgnoreCase(contentType);

        if (!isXlsx && !isXls) {
            throw new IllegalArgumentException("El archivo debe ser Excel (.xls o .xlsx)");
        }
    }

    private void validateCsvFile(MultipartFile file) {
        validateFilePresent(file);

        String lowerName = Optional.ofNullable(file.getOriginalFilename()).orElse("").toLowerCase(Locale.ROOT);
        String contentType = file.getContentType();

        boolean isCsv = lowerName.endsWith(".csv") || CONTENT_TYPE_CSV.equalsIgnoreCase(contentType);
        if (!isCsv) {
            throw new IllegalArgumentException("El archivo debe ser CSV (.csv)");
        }
    }

    private void validateFilePresent(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo es requerido");
        }
    }

    private record RowKey(Long standId, Long issuerId, Long customerId, LocalDate issueDate, BigDecimal igv) {
    }

    private static class BulkDebtRowAccumulator {
        private final Long standId;
        private final Long issuerId;
        private final Long customerId;
        private final LocalDate issueDate;
        private final BigDecimal igv;
        private final List<VoucherItemRequestDTO> items = new ArrayList<>();

        private BulkDebtRowAccumulator(Long standId, Long issuerId, Long customerId, LocalDate issueDate, BigDecimal igv) {
            this.standId = standId;
            this.issuerId = issuerId;
            this.customerId = customerId;
            this.issueDate = issueDate;
            this.igv = igv;
        }

        private BulkDebtRowRequestDTO toDto() {
            return new BulkDebtRowRequestDTO(standId, issuerId, customerId, issueDate, igv, items);
        }
    }

    private interface RowReader {
        int rowNumber();

        Long readLong(String name);

        BigDecimal readBigDecimal(String name);

        LocalDate readLocalDate(String name);

        MeasureUnitType readMeasureUnitType(String name);
    }

    private record ExcelRowReader(Row row, Map<String, Integer> cols) implements RowReader {

        @Override
            public int rowNumber() {
                return row.getRowNum() + 1;
            }

            @Override
            public Long readLong(String name) {
                return longValue(requiredCell(name), name);
            }

            @Override
            public BigDecimal readBigDecimal(String name) {
                return decimalValue(requiredCell(name), name);
            }

            @Override
            public LocalDate readLocalDate(String name) {
                Integer idx = cols.get(normalize(name));
                if (idx == null) {
                    return null;
                }
                Cell c = row.getCell(idx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (c == null) {
                    return null;
                }

                try {
                    if (c.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(c)) {
                        return c.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    }
                    String v = cellToString(c);
                    if (v == null || v.isBlank()) {
                        return null;
                    }
                    return LocalDate.parse(v.trim());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fila " + rowNumber() + ": '" + name + "' inválido (use YYYY-MM-DD)");
                }
            }

            @Override
            public MeasureUnitType readMeasureUnitType(String name) {
                String raw = stringValue(requiredCell(name), name);
                try {
                    return MeasureUnitType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fila " + rowNumber() + ": " + name + " inválido: '" + raw + "'");
                }
            }

            private Cell requiredCell(String name) {
                Integer idx = cols.get(normalize(name));
                if (idx == null) {
                    throw new IllegalArgumentException("Falta columna: " + name);
                }
                Cell c = row.getCell(idx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (c == null) {
                    throw new IllegalArgumentException("Fila " + rowNumber() + ": '" + name + "' es requerido");
                }
                return c;
            }

            private String stringValue(Cell c, String name) {
                String v = cellToString(c);
                if (v == null || v.isBlank()) {
                    throw new IllegalArgumentException("Fila " + rowNumber() + ": '" + name + "' es requerido");
                }
                return v.trim();
            }

            private Long longValue(Cell c, String name) {
                try {
                    if (c.getCellType() == CellType.NUMERIC) {
                        return (long) c.getNumericCellValue();
                    }
                    return Long.parseLong(stringValue(c, name));
                } catch (IllegalArgumentException e) {
                    throw e;
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fila " + rowNumber() + ": '" + name + "' inválido");
                }
            }

            private BigDecimal decimalValue(Cell c, String name) {
                try {
                    if (c.getCellType() == CellType.NUMERIC) {
                        return BigDecimal.valueOf(c.getNumericCellValue());
                    }
                    return new BigDecimal(stringValue(c, name).replace(",", "."));
                } catch (IllegalArgumentException e) {
                    throw e;
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fila " + rowNumber() + ": '" + name + "' inválido");
                }
            }

            private static String normalize(String s) {
                return s == null ? "" : s.replaceAll("\\s+", "").trim().toLowerCase(Locale.ROOT);
            }
        }

    private record CsvRecordReader(CSVRecord record, Map<String, Integer> cols, int rowNumber) implements RowReader {

        @Override
            public Long readLong(String name) {
                return parseLong(requiredText(name), name);
            }

            @Override
            public BigDecimal readBigDecimal(String name) {
                return parseBigDecimal(requiredText(name), name);
            }

            @Override
            public LocalDate readLocalDate(String name) {
                String v = optionalText(name);
                if (v == null || v.isBlank()) {
                    return null;
                }
                try {
                    return LocalDate.parse(v);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fila " + rowNumber + ": '" + name + "' inválido (use YYYY-MM-DD)");
                }
            }

            @Override
            public MeasureUnitType readMeasureUnitType(String name) {
                String raw = requiredText(name);
                try {
                    return MeasureUnitType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fila " + rowNumber + ": " + name + " inválido: '" + raw + "'");
                }
            }

            private String requiredText(String name) {
                String v = get(name);
                if (v == null || v.isBlank()) {
                    throw new IllegalArgumentException("Fila " + rowNumber + ": '" + name + "' es requerido");
                }
                return v.trim();
            }

            private String optionalText(String name) {
                String v = get(name);
                return v == null ? null : v.trim();
            }

            private String get(String name) {
                Integer idx = cols.get(normalize(name));
                if (idx == null) {
                    throw new IllegalArgumentException("Falta columna: " + name);
                }
                return idx < record.size() ? record.get(idx) : null;
            }

            private Long parseLong(String v, String name) {
                try {
                    return Long.parseLong(v.trim());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fila " + rowNumber + ": '" + name + "' inválido");
                }
            }

            private BigDecimal parseBigDecimal(String v, String name) {
                try {
                    return new BigDecimal(v.trim().replace(",", "."));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fila " + rowNumber + ": '" + name + "' inválido");
                }
            }

            private static String normalize(String s) {
                return s == null ? "" : s.replaceAll("\\s+", "").trim().toLowerCase(Locale.ROOT);
            }
        }
}
