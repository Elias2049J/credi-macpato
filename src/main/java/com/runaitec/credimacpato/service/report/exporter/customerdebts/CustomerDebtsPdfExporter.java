package com.runaitec.credimacpato.service.report.exporter.customerdebts;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.runaitec.credimacpato.dto.report.CustomerDebtsReport;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractPdfExporter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerDebtsPdfExporter extends AbstractPdfExporter<CustomerDebtsReport> {

    @Override
    protected void writePdf(Document doc, CustomerDebtsReport report) {
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
    protected String buildFilename(CustomerDebtsReport report) {
        return "vendor-debts-" + (report.getCustomerId() == null ? "unknown" : report.getCustomerId()) + ".pdf";
    }
}
