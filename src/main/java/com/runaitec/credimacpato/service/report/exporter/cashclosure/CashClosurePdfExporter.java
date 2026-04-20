package com.runaitec.credimacpato.service.report.exporter.cashclosure;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.VouchersByStandDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractPdfExporter;
import org.springframework.stereotype.Component;

@Component
public class CashClosurePdfExporter extends AbstractPdfExporter<CashClosureReport> {

    @Override
    protected void writePdf(Document doc, CashClosureReport report) {
        doc.add(new Paragraph("CASH CLOSURE REPORT"));
        doc.add(new Paragraph("Owner: " + (report.getOwner() == null ? "-" : report.getOwner().getFullName())));
        doc.add(new Paragraph("Total sales today: " + n(report.getTotalSalesToday())));
        doc.add(new Paragraph("Total debt today: " + n(report.getTotalDebtToday())));
        doc.add(new Paragraph("Total vouchers issued today: " + (report.getTotalVouchersCountToday() == null ? 0 : report.getTotalVouchersCountToday())));
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("By stand:"));

        if (report.getVouchersByStand() != null) {
            for (VouchersByStandDTO s : report.getVouchersByStand()) {
                String line = "- Stand " + (s.getStand() == null ? "?" : s.getStand().getNumber())
                        + " (id=" + (s.getStand() == null ? null : s.getStand().getId()) + ")"
                        + " sales=" + n(s.getTotalSales())
                        + " debt=" + n(s.getTotalDebt())
                        + " issued=" + s.getTotalIssuedVouchersCount();
                doc.add(new Paragraph(line));
            }
        }
    }

    @Override
    protected String buildFilename(CashClosureReport report) {
        String ownerLabel = report.getOwner() == null ? "" : ("-" + sanitizeFilenamePart(report.getOwner().getFullName()));
        return "cash-closure" + ownerLabel;
    }
}

