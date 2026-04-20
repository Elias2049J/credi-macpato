package com.runaitec.credimacpato.service.report.exporter.partnerdebts;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.runaitec.credimacpato.dto.report.PartnerDebtsReport;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractPdfExporter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PartnerDebtsPdfExporter extends AbstractPdfExporter<PartnerDebtsReport> {

    @Override
    protected void writePdf(Document doc, PartnerDebtsReport report) {
        doc.add(new Paragraph("PARTNER DEBTS REPORT"));
        doc.add(new Paragraph("CustomerId: " + report.getCustomerId()));
        doc.add(new Paragraph("Vouchers: " + (report.getVouchers() == null ? 0 : report.getVouchers().size())));
        doc.add(new Paragraph(""));

        if (report.getVouchers() != null) {
            for (VoucherResponseDTO v : report.getVouchers()) {
                String line = "- " + Objects.toString(v.getSerialNumber(), "")
                        + " | issueDate=" + v.getIssueDate()
                        + " | standId=" + v.getStandId()
                        + " | state=" + v.getState()
                        + " | payable=" + n(v.getPayableAmount());
                doc.add(new Paragraph(line));
            }
        }
    }

    @Override
    protected String buildFilename(PartnerDebtsReport report) {
        return "partner-debts-" + (report.getCustomerId() == null ? "unknown" : report.getCustomerId()) + ".pdf";
    }
}
