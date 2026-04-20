package com.runaitec.credimacpato.service.report.exporter.cashclosure;

import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.VouchersByStandDTO;
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
public class CashClosureExcelExporter extends AbstractExcelExporter<CashClosureReport> {

    @Override
    protected void writeExcel(XSSFWorkbook workbook, CashClosureReport report) {
        writeSummarySheet(workbook, report);
        writeStandsSheet(workbook, report.getVouchersByStand());
        writeVouchersSheet(workbook, report.getVouchersByStand());
    }

    @Override
    protected String buildFilename(CashClosureReport report) {
        String ownerLabel = report.getOwner() == null ? "" : ("-" + sanitizeFilenamePart(report.getOwner().getFullName()));
        return "cash-closure" + ownerLabel + ".xlsx";
    }

    private void writeSummarySheet(XSSFWorkbook wb, CashClosureReport report) {
        Sheet s = wb.createSheet("Resumen");

        Row r0 = s.createRow(0);
        r0.createCell(0).setCellValue("Owner");
        r0.createCell(1).setCellValue(report.getOwner() == null ? "" : report.getOwner().getFullName());

        Row r1 = s.createRow(1);
        r1.createCell(0).setCellValue("TotalSalesToday");
        r1.createCell(1).setCellValue(n(report.getTotalSalesToday()).doubleValue());

        Row r2 = s.createRow(2);
        r2.createCell(0).setCellValue("TotalDebtToday");
        r2.createCell(1).setCellValue(n(report.getTotalDebtToday()).doubleValue());

        Row r3 = s.createRow(3);
        r3.createCell(0).setCellValue("TotalVouchersCountToday");
        r3.createCell(1).setCellValue(report.getTotalVouchersCountToday() == null ? 0 : report.getTotalVouchersCountToday());

        autoSizeColumns(s, 1);
    }

    private void writeStandsSheet(XSSFWorkbook wb, List<VouchersByStandDTO> vouchersByStand) {
        Sheet s = wb.createSheet("Stands");
        writeHeaderRow(s, "standId", "standNumber", "issuedCount", "totalSales", "totalDebt");

        int r = 1;
        if (vouchersByStand != null) {
            for (VouchersByStandDTO vb : vouchersByStand) {
                Row row = s.createRow(r++);
                row.createCell(0).setCellValue(vb.getStand() == null || vb.getStand().getId() == null ? 0 : vb.getStand().getId());
                row.createCell(1).setCellValue(vb.getStand() == null || vb.getStand().getNumber() == null ? 0 : vb.getStand().getNumber());
                row.createCell(2).setCellValue(vb.getTotalIssuedVouchersCount() == null ? 0 : vb.getTotalIssuedVouchersCount());
                row.createCell(3).setCellValue(n(vb.getTotalSales()).doubleValue());
                row.createCell(4).setCellValue(n(vb.getTotalDebt()).doubleValue());
            }
        }

        autoSizeColumns(s, 4);
    }

    private void writeVouchersSheet(XSSFWorkbook wb, List<VouchersByStandDTO> vouchersByStand) {
        Sheet s = wb.createSheet("Vouchers");
        writeHeaderRow(s, "standId", "voucherId", "serialNumber", "issueDate", "state", "payableAmount", "itemsCount", "itemsPaidCount");

        int r = 1;
        if (vouchersByStand != null) {
            for (VouchersByStandDTO vb : vouchersByStand) {
                Long standId = vb.getStand() == null ? null : vb.getStand().getId();
                if (vb.getVouchers() == null) {
                    continue;
                }
                for (VoucherResponseDTO v : vb.getVouchers()) {
                    Row row = s.createRow(r++);
                    row.createCell(0).setCellValue(standId == null ? 0 : standId);
                    row.createCell(1).setCellValue(v.getId() == null ? 0 : v.getId());
                    row.createCell(2).setCellValue(Objects.toString(v.getSerialNumber(), ""));
                    row.createCell(3).setCellValue(v.getIssueDate() == null ? "" : v.getIssueDate().toString());
                    row.createCell(4).setCellValue(v.getState() == null ? "" : v.getState().name());
                    row.createCell(5).setCellValue(n(v.getPayableAmount()).doubleValue());

                    int itemsCount = v.getVoucherItems() == null ? 0 : v.getVoucherItems().size();
                    int itemsPaid = v.getVoucherItems() == null ? 0 : (int) v.getVoucherItems().stream().filter(this::isPaid).count();
                    row.createCell(6).setCellValue(itemsCount);
                    row.createCell(7).setCellValue(itemsPaid);
                }
            }
        }

        autoSizeColumns(s, 7);
    }

    private boolean isPaid(VoucherItemResponseDTO item) {
        return item != null && item.getState() != null && item.getState().isPaid();
    }
}
