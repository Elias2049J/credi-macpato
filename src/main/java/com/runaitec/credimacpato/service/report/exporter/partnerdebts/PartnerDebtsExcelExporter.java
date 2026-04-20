package com.runaitec.credimacpato.service.report.exporter.partnerdebts;

import com.runaitec.credimacpato.dto.report.PartnerDebtsReport;
import com.runaitec.credimacpato.dto.voucher.VoucherItemResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractExcelExporter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class PartnerDebtsExcelExporter extends AbstractExcelExporter<PartnerDebtsReport> {

    @Override
    protected void writeExcel(XSSFWorkbook workbook, PartnerDebtsReport report) {
        writeSummarySheet(workbook, report);
        writeVouchersSheet(workbook, report.getVouchers());
        writeItemsSheet(workbook, report.getVouchers());
    }

    @Override
    protected String buildFilename(PartnerDebtsReport report) {
        return "partner-debts-" + (report.getCustomerId() == null ? "unknown" : report.getCustomerId()) + ".xlsx";
    }

    private void writeSummarySheet(XSSFWorkbook wb, PartnerDebtsReport report) {
        Sheet s = wb.createSheet("Resumen");

        Row a = s.createRow(0);
        a.createCell(0).setCellValue("customerId");
        a.createCell(1).setCellValue(report.getCustomerId() == null ? 0 : report.getCustomerId());

        Row b = s.createRow(1);
        b.createCell(0).setCellValue("vouchersCount");
        b.createCell(1).setCellValue(report.getVouchers() == null ? 0 : report.getVouchers().size());

        autoSizeColumns(s, 1);
    }

    private void writeVouchersSheet(XSSFWorkbook wb, List<VoucherResponseDTO> vouchers) {
        Sheet s = wb.createSheet("Vouchers");
        writeHeaderRow(s, "voucherId", "serialNumber", "issueDate", "state", "standId", "payableAmount", "itemsCount", "itemsPendingCount");

        int r = 1;
        if (vouchers != null) {
            for (VoucherResponseDTO v : vouchers) {
                int itemsCount = v.getVoucherItems() == null ? 0 : v.getVoucherItems().size();
                int pendingCount = v.getVoucherItems() == null ? 0 : (int) v.getVoucherItems().stream().filter(i -> i != null && i.getState() != null && i.getState().idPending()).count();

                Row row = s.createRow(r++);
                row.createCell(0).setCellValue(v.getId() == null ? 0 : v.getId());
                row.createCell(1).setCellValue(Objects.toString(v.getSerialNumber(), ""));
                row.createCell(2).setCellValue(v.getIssueDate() == null ? "" : v.getIssueDate().toString());
                row.createCell(3).setCellValue(v.getState() == null ? "" : v.getState().name());
                row.createCell(4).setCellValue(v.getStandId() == null ? 0 : v.getStandId());
                row.createCell(5).setCellValue(n(v.getPayableAmount()).doubleValue());
                row.createCell(6).setCellValue(itemsCount);
                row.createCell(7).setCellValue(pendingCount);
            }
        }

        autoSizeColumns(s, 7);
    }

    private void writeItemsSheet(XSSFWorkbook wb, List<VoucherResponseDTO> vouchers) {
        Sheet s = wb.createSheet("Items");
        writeHeaderRow(s, "voucherId", "itemId", "state", "quantity", "measureUnitType", "chargeId", "chargeDescription", "unitValue", "payableAmount");

        int r = 1;
        if (vouchers != null) {
            for (VoucherResponseDTO v : vouchers) {
                if (v.getVoucherItems() == null) {
                    continue;
                }
                for (VoucherItemResponseDTO it : v.getVoucherItems()) {
                    Row row = s.createRow(r++);
                    row.createCell(0).setCellValue(v.getId() == null ? 0 : v.getId());
                    row.createCell(1).setCellValue(it.getId() == null ? 0 : it.getId());
                    row.createCell(2).setCellValue(it.getState() == null ? "" : it.getState().name());
                    row.createCell(3).setCellValue(n(it.getQuantity()).doubleValue());
                    row.createCell(4).setCellValue(it.getMeasureUnitType() == null ? "" : it.getMeasureUnitType().name());
                    row.createCell(5).setCellValue(it.getCharge() == null || it.getCharge().getId() == null ? 0 : it.getCharge().getId());
                    row.createCell(6).setCellValue(it.getCharge() == null ? "" : Objects.toString(it.getCharge().getDescription(), ""));
                    row.createCell(7).setCellValue(n(it.getUnitValue()).doubleValue());
                    row.createCell(8).setCellValue(n(it.getPayableAmount()).doubleValue());
                }
            }
        }

        autoSizeColumns(s, 8);
    }
}
