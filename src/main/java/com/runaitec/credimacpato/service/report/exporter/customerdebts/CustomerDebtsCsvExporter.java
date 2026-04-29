package com.runaitec.credimacpato.service.report.exporter.customerdebts;

import com.runaitec.credimacpato.dto.report.CustomerDebtsReport;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractCsvExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class CustomerDebtsCsvExporter extends AbstractCsvExporter<CustomerDebtsReport> {

    @Override
    protected byte[] exportBytes(CustomerDebtsReport report) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
             CSVPrinter p = new CSVPrinter(osw, CSVFormat.DEFAULT
                     .builder()
                     .setHeader("Sección", "Clave", "Valor")
                     .build())) {

            p.printRecord("Resumen", "Cliente", report.getCustomer() == null ? "" : report.getCustomer().getFullName());
            p.printRecord("Resumen", "Total de comprobantes", report.getTotalVouchersCount());
            p.printRecord("Resumen", "Deuda total", n(report.getTotalDebt()));
            p.println();

            p.printRecord("Comprobantes", "N°", "Serie", "Fecha emisión", "Estado", "Monto total", "Ítems", "Ítems pendientes");
            if (report.getVouchers() != null) {
                int contador = 1;
                for (VoucherResponseDTO v : report.getVouchers()) {
                    int itemsCount = v.getVoucherItems() == null ? 0 : v.getVoucherItems().size();
                    int pendingCount = v.getVoucherItems() == null ? 0 :
                            (int) v.getVoucherItems().stream()
                                    .filter(i -> i != null && i.getState() != null && i.getState().idPending())
                                    .count();

                    p.printRecord(
                            "Comprobantes",
                            contador++,
                            Objects.toString(v.getSerialNumber(), ""),
                            v.getIssueDate() == null ? "" : v.getIssueDate().toString(),
                            v.getState() == null ? "" : v.getState().name(),
                            n(v.getPayableAmount()),
                            itemsCount,
                            pendingCount
                    );
                }
            }

            osw.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo exportar CSV", e);
        }
    }

    @Override
    protected String buildFilename(CustomerDebtsReport report) {
        String cliente = report.getCustomer() == null ? ""
                : ("-" + sanitizeFilenamePart(report.getCustomer().getFullName()));
        return "deudas-cliente" + cliente + "-" + report.getCreatedAt() + ".csv";
    }
}

