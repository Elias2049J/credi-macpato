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
        writeResumenSheet(workbook, report);
        writeStandsSheet(workbook, report.getVouchersByStand());
        writeVouchersSheet(workbook, report.getVouchersByStand());
    }

    @Override
    protected String buildFilename(CashClosureReport report) {
        String propietario = report.getOwner() == null ? ""
                : ("-" + sanitizeFilenamePart(report.getOwner().getFullName()));
        return "cierre-caja" + propietario + "-" + report.getCreatedAt() + ".xlsx";
    }

    private void writeResumenSheet(XSSFWorkbook wb, CashClosureReport report) {
        Sheet s = wb.createSheet("Resumen");

        Row r0 = s.createRow(0);
        r0.createCell(0).setCellValue("Propietario");
        r0.createCell(1).setCellValue(report.getOwner() == null ? "" : report.getOwner().getFullName());

        Row r1 = s.createRow(1);
        r1.createCell(0).setCellValue("Ventas totales del día");
        r1.createCell(1).setCellValue(n(report.getTotalSalesToday()).doubleValue());

        Row r2 = s.createRow(2);
        r2.createCell(0).setCellValue("Deuda total del día");
        r2.createCell(1).setCellValue(n(report.getTotalDebtToday()).doubleValue());

        Row r3 = s.createRow(3);
        r3.createCell(0).setCellValue("Comprobantes emitidos hoy");
        r3.createCell(1).setCellValue(report.getTotalVouchersCountToday() == null ? 0 : report.getTotalVouchersCountToday());

        autoSizeColumns(s, 1);
    }

    private void writeStandsSheet(XSSFWorkbook wb, List<VouchersByStandDTO> vouchersByStand) {
        Sheet s = wb.createSheet("Puestos");
        writeHeaderRow(s, "N°", "Número de puesto", "Comprobantes emitidos", "Ventas", "Deuda");

        int r = 1;
        int contador = 1;
        if (vouchersByStand != null) {
            for (VouchersByStandDTO vb : vouchersByStand) {
                Row row = s.createRow(r++);
                row.createCell(0).setCellValue(contador++);
                row.createCell(1).setCellValue(vb.getStand() == null || vb.getStand().getNumber() == null ? 0 : vb.getStand().getNumber());
                row.createCell(2).setCellValue(vb.getTotalIssuedVouchersCount() == null ? 0 : vb.getTotalIssuedVouchersCount());
                row.createCell(3).setCellValue(n(vb.getTotalSales()).doubleValue());
                row.createCell(4).setCellValue(n(vb.getTotalDebt()).doubleValue());
            }
        }

        autoSizeColumns(s, 4);
    }

    private void writeVouchersSheet(XSSFWorkbook wb, List<VouchersByStandDTO> vouchersByStand) {
        Sheet s = wb.createSheet("Comprobantes");
        writeHeaderRow(s, "N°", "Serie", "Fecha emisión", "Estado", "Monto total", "Ítems", "Ítems pagados");

        int r = 1;
        int contador = 1;
        if (vouchersByStand != null) {
            for (VouchersByStandDTO vb : vouchersByStand) {
                if (vb.getVouchers() == null) continue;

                for (VoucherResponseDTO v : vb.getVouchers()) {
                    Row row = s.createRow(r++);
                    row.createCell(0).setCellValue(contador++);
                    row.createCell(1).setCellValue(Objects.toString(v.getSerialNumber(), ""));
                    row.createCell(2).setCellValue(v.getIssueDate() == null ? "" : v.getIssueDate().toString());
                    row.createCell(3).setCellValue(v.getState() == null ? "" : v.getState().name());
                    row.createCell(4).setCellValue(n(v.getPayableAmount()).doubleValue());

                    int itemsCount = v.getVoucherItems() == null ? 0 : v.getVoucherItems().size();
                    int itemsPaid = v.getVoucherItems() == null ? 0 : (int) v.getVoucherItems().stream().filter(this::isPaid).count();
                    row.createCell(5).setCellValue(itemsCount);
                    row.createCell(6).setCellValue(itemsPaid);
                }
            }
        }

        autoSizeColumns(s, 6);
    }

    private boolean isPaid(VoucherItemResponseDTO item) {
        return item != null && item.getState() != null && item.getState().isPaid();
    }
}

