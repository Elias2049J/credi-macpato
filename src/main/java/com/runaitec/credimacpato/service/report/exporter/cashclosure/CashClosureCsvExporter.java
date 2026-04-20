package com.runaitec.credimacpato.service.report.exporter.cashclosure;

import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.VouchersByStandDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractCsvExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Component
public class CashClosureCsvExporter extends AbstractCsvExporter<CashClosureReport> {

    @Override
    protected byte[] exportBytes(CashClosureReport report) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
             CSVPrinter p = new CSVPrinter(osw, CSVFormat.DEFAULT
                     .builder()
                     .setHeader("section", "key", "value")
                     .build())) {

            p.printRecord("summary", "owner", report.getOwner() == null ? null : report.getOwner().getFullName());
            p.printRecord("summary", "date", LocalDate.now());
            p.printRecord("summary", "totalSalesToday", n(report.getTotalSalesToday()));
            p.printRecord("summary", "totalDebtToday", n(report.getTotalDebtToday()));
            p.printRecord("summary", "totalVouchersCountToday", report.getTotalVouchersCountToday());

            p.println();
            p.printRecord("stands", "standId", "standNumber", "issuedCount", "totalSales", "totalDebt");
            if (report.getVouchersByStand() != null) {
                for (VouchersByStandDTO s : report.getVouchersByStand()) {
                    Long standId = s.getStand() == null ? null : s.getStand().getId();
                    Integer standNumber = s.getStand() == null ? null : s.getStand().getNumber();
                    p.printRecord("stands", standId, standNumber, s.getTotalIssuedVouchersCount(), n(s.getTotalSales()), n(s.getTotalDebt()));
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
        String ownerLabel = report.getOwner() == null ? "" : ("-" + sanitizeFilenamePart(report.getOwner().getFullName()));
        return "cash-closure" + ownerLabel + ".csv";
    }
}
