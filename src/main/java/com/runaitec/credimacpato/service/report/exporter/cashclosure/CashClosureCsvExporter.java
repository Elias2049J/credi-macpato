package com.runaitec.credimacpato.service.report.exporter.cashclosure;

import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.VouchersByStandDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherItemResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractCsvExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Objects;

@Component
public class CashClosureCsvExporter extends AbstractCsvExporter<CashClosureReport> {

    @Override
    protected byte[] exportBytes(CashClosureReport report) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
             CSVPrinter p = new CSVPrinter(osw, CSVFormat.DEFAULT
                     .builder()
                     .setHeader("Sección", "Clave", "Valor")
                     .build())) {

            p.printRecord("Resumen", "Propietario", report.getOwner() == null ? "" : report.getOwner().getFullName());
            p.printRecord("Resumen", "Fecha", LocalDate.now());
            p.printRecord("Resumen", "Ventas totales del día", n(report.getTotalSalesToday()));
            p.printRecord("Resumen", "Deuda total del día", n(report.getTotalDebtToday()));
            p.printRecord("Resumen", "Comprobantes emitidos hoy", report.getTotalVouchersCountToday());
            p.println();

            p.printRecord("Puestos", "N°", "Número de puesto", "Comprobantes emitidos", "Ventas", "Deuda");
            if (report.getVouchersByStand() != null) {
                int contador = 1;
                for (VouchersByStandDTO s : report.getVouchersByStand()) {
                    Integer standNumber = s.getStand() == null ? null : s.getStand().getNumber();
                    p.printRecord("Puestos", contador++, standNumber,
                            s.getTotalIssuedVouchersCount(), n(s.getTotalSales()), n(s.getTotalDebt()));
                }
            }
            p.println();

            p.printRecord("Comprobantes", "N°", "Serie", "Fecha emisión", "Estado", "Monto total", "Ítems", "Ítems pagados");
            if (report.getVouchersByStand() != null) {
                int contadorVoucher = 1;
                for (VouchersByStandDTO s : report.getVouchersByStand()) {
                    if (s.getVouchers() == null) continue;
                    for (VoucherResponseDTO v : s.getVouchers()) {
                        int itemsCount = v.getVoucherItems() == null ? 0 : v.getVoucherItems().size();
                        int itemsPaid = v.getVoucherItems() == null ? 0 :
                                (int) v.getVoucherItems().stream().filter(this::isPaid).count();

                        p.printRecord("Comprobantes", contadorVoucher++,
                                Objects.toString(v.getSerialNumber(), ""),
                                v.getIssueDate() == null ? "" : v.getIssueDate().toString(),
                                v.getState() == null ? "" : v.getState().name(),
                                n(v.getPayableAmount()), itemsCount, itemsPaid);
                    }
                }
            }

            osw.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo exportar CSV", e);
        }
    }

    @Override
    protected String buildFilename(CashClosureReport report) {
        String propietario = report.getOwner() == null ? ""
                : ("-" + sanitizeFilenamePart(report.getOwner().getFullName()));
        return "cierre-caja" + propietario + "-" + report.getCreatedAt() + ".csv";
    }

    private boolean isPaid(VoucherItemResponseDTO item) {
        return item != null && item.getState() != null && item.getState().isPaid();
    }
}

