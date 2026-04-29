package com.runaitec.credimacpato.service.report.exporter.customerdebts;

import com.runaitec.credimacpato.dto.report.CustomerDebtsReport;
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
public class CustomerDebtsExcelExporter extends AbstractExcelExporter<CustomerDebtsReport> {

    @Override
    protected void writeExcel(XSSFWorkbook workbook, CustomerDebtsReport report) {
        writeResumenSheet(workbook, report);
        writeVouchersSheet(workbook, report.getVouchers());
        writeItemsSheet(workbook, report.getVouchers());
    }

    @Override
    protected String buildFilename(CustomerDebtsReport report) {
        String cliente = report.getCustomer() == null ? ""
                : ("-" + sanitizeFilenamePart(report.getCustomer().getFullName()));
        return "deudas-cliente" + cliente + "-" + report.getCreatedAt() + ".xlsx";
    }

    private void writeResumenSheet(XSSFWorkbook wb, CustomerDebtsReport report) {
        Sheet s = wb.createSheet("Resumen");

        Row r0 = s.createRow(0);
        r0.createCell(0).setCellValue("Cliente");
        r0.createCell(1).setCellValue(report.getCustomer() == null ? "" : report.getCustomer().getFullName());

        Row r1 = s.createRow(1);
        r1.createCell(0).setCellValue("Total de comprobantes");
        r1.createCell(1).setCellValue(report.getTotalVouchersCount());

        Row r2 = s.createRow(2);
        r2.createCell(0).setCellValue("Deuda total");
        r2.createCell(1).setCellValue(n(report.getTotalDebt()).doubleValue());

        autoSizeColumns(s, 1);
    }

    private void writeVouchersSheet(XSSFWorkbook wb, List<VoucherResponseDTO> vouchers) {
        Sheet s = wb.createSheet("Comprobantes");
        writeHeaderRow(s, "N°", "Serie", "Fecha emisión", "Estado", "Monto total", "Ítems", "Ítems pendientes");

        int r = 1;
        int contador = 1;
        if (vouchers != null) {
            for (VoucherResponseDTO v : vouchers) {
                int itemsCount = v.getVoucherItems() == null ? 0 : v.getVoucherItems().size();
                int pendingCount = v.getVoucherItems() == null ? 0 :
                        (int) v.getVoucherItems().stream()
                                .filter(i -> i != null && i.getState() != null && i.getState().idPending())
                                .count();

                Row row = s.createRow(r++);
                row.createCell(0).setCellValue(contador++);
                row.createCell(1).setCellValue(Objects.toString(v.getSerialNumber(), ""));
                row.createCell(2).setCellValue(v.getIssueDate() == null ? "" : v.getIssueDate().toString());
                row.createCell(3).setCellValue(v.getState() == null ? "" : v.getState().name());
                row.createCell(4).setCellValue(n(v.getPayableAmount()).doubleValue());
                row.createCell(5).setCellValue(itemsCount);
                row.createCell(6).setCellValue(pendingCount);
            }
        }

        autoSizeColumns(s, 6);
    }

    private void writeItemsSheet(XSSFWorkbook wb, List<VoucherResponseDTO> vouchers) {
        Sheet s = wb.createSheet("Ítems");
        writeHeaderRow(s, "N° comprobante", "N° ítem", "Estado", "Cantidad", "Unidad medida", "Concepto", "Descripción", "Valor unitario", "Monto total");

        int r = 1;
        int contadorVoucher = 1;
        if (vouchers != null) {
            for (VoucherResponseDTO v : vouchers) {
                if (v.getVoucherItems() == null) continue;

                int contadorItem = 1;
                for (VoucherItemResponseDTO it : v.getVoucherItems()) {
                    Row row = s.createRow(r++);
                    row.createCell(0).setCellValue(contadorVoucher);
                    row.createCell(1).setCellValue(contadorItem++);
                    row.createCell(2).setCellValue(it.getState() == null ? "" : it.getState().name());
                    row.createCell(3).setCellValue(n(it.getQuantity()).doubleValue());
                    row.createCell(4).setCellValue(it.getMeasureUnitType() == null ? "" : it.getMeasureUnitType().name());
                    row.createCell(6).setCellValue(it.getCharge() == null ? "" : Objects.toString(it.getCharge().getDescription(), ""));
                    row.createCell(7).setCellValue(n(it.getUnitValue()).doubleValue());
                    row.createCell(8).setCellValue(n(it.getPayableAmount()).doubleValue());
                }
                contadorVoucher++;
            }
        }

        autoSizeColumns(s, 8);
    }
}
