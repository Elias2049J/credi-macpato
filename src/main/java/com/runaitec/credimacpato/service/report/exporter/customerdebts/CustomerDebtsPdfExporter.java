package com.runaitec.credimacpato.service.report.exporter.customerdebts;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.runaitec.credimacpato.dto.report.CustomerDebtsReport;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractPdfExporter;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class CustomerDebtsPdfExporter extends AbstractPdfExporter<CustomerDebtsReport> {

    @Override
    protected void writePdf(Document doc, CustomerDebtsReport report) {
        Paragraph titulo = new Paragraph("REPORTE DE DEUDAS")
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER);
        doc.add(titulo);
        doc.add(new Paragraph("\n"));

        doc.add(new Paragraph("Titular: " +
                (report.getCustomer() == null ? "-" : report.getCustomer().getFullName())));
        doc.add(new Paragraph("Total de comprobantes: " + report.getTotalVouchersCount()));
        doc.add(new Paragraph("Deuda total: " + n(report.getTotalDebt())));
        doc.add(new Paragraph("\n"));

        if (report.getVouchers() != null && !report.getVouchers().isEmpty()) {
            Table tablaVouchers = new Table(6);
            tablaVouchers.addCell(new Cell().add(new Paragraph("N°")).setBold());
            tablaVouchers.addCell(new Cell().add(new Paragraph("Serie")).setBold());
            tablaVouchers.addCell(new Cell().add(new Paragraph("Estado")).setBold());
            tablaVouchers.addCell(new Cell().add(new Paragraph("Fecha emisión")).setBold());
            tablaVouchers.addCell(new Cell().add(new Paragraph("Monto pagado")).setBold());
            tablaVouchers.addCell(new Cell().add(new Paragraph("Monto pendiente")).setBold());

            int contador = 1;
            for (VoucherResponseDTO v : report.getVouchers()) {
                tablaVouchers.addCell(new Paragraph(String.valueOf(contador++)));
                tablaVouchers.addCell(new Paragraph(Objects.toString(v.getSerialNumber(), "")));
                tablaVouchers.addCell(new Paragraph(v.getState().name()));
                tablaVouchers.addCell(new Paragraph(v.getIssueDate().toString()));
                tablaVouchers.addCell(new Paragraph(n(v.getPaidAmount()).toString()));
                tablaVouchers.addCell(new Paragraph(n(v.getPendingAmount()).toString()));
            }

            doc.add(tablaVouchers);
        }
    }

    @Override
    protected String buildFilename(CustomerDebtsReport report) {
        String cliente = report.getCustomer() == null ? ""
                : ("-" + sanitizeFilenamePart(report.getCustomer().getFullName()));
        return "deudas-cliente" + cliente + "-" + report.getCreatedAt();
    }
}

