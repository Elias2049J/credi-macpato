package com.runaitec.credimacpato.service.report.exporter.partnerdebts;

import com.runaitec.credimacpato.dto.report.PartnerDebtsReport;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractCsvExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Component
public class PartnerDebtsCsvExporter extends AbstractCsvExporter<PartnerDebtsReport> {

    @Override
    protected byte[] exportBytes(PartnerDebtsReport report) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
             CSVPrinter p = new CSVPrinter(osw, CSVFormat.DEFAULT
                     .builder()
                     .setHeader(
                             "voucherId",
                             "serialNumber",
                             "issueDate",
                             "state",
                             "standId",
                             "payableAmount",
                             "itemsCount",
                             "itemsPendingCount")
                     .build())) {

            if (report.getVouchers() != null) {
                for (VoucherResponseDTO v : report.getVouchers()) {
                    int itemsCount = v.getVoucherItems() == null ? 0 : v.getVoucherItems().size();
                    int pendingCount = v.getVoucherItems() == null ? 0 : (int) v.getVoucherItems().stream().filter(i -> i != null && i.getState() != null && i.getState().idPending()).count();

                    p.printRecord(
                            v.getId(),
                            v.getSerialNumber(),
                            v.getIssueDate(),
                            v.getState() == null ? null : v.getState().name(),
                            v.getStandId(),
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
    protected String buildFilename(PartnerDebtsReport report) {
        return "partner-debts-" + (report.getCustomerId() == null ? "unknown" : report.getCustomerId()) + ".csv";
    }
}
