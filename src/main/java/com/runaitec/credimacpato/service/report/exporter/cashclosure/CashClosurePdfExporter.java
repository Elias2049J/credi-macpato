package com.runaitec.credimacpato.service.report.exporter.cashclosure;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.VouchersByStandDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.report.exporter.AbstractPdfExporter;
import org.springframework.stereotype.Component;
@Component
public class CashClosurePdfExporter extends AbstractPdfExporter<CashClosureReport> {

    @Override
    protected void writePdf(Document doc, CashClosureReport report) {
        Paragraph titulo = new Paragraph("REPORTE DE CIERRE DE CAJA")
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER);
        doc.add(titulo);
        doc.add(new Paragraph("\n"));

        doc.add(new Paragraph("Propietario: " +
                (report.getOwner() == null ? "-" : report.getOwner().getFullName())));
        doc.add(new Paragraph("Ventas totales del día: " + n(report.getTotalSalesToday())));
        doc.add(new Paragraph("Deuda total del día: " + n(report.getTotalDebtToday())));
        doc.add(new Paragraph("Comprobantes emitidos hoy: " +
                (report.getTotalVouchersCountToday() == null ? 0 : report.getTotalVouchersCountToday())));
        doc.add(new Paragraph("\n"));

        if (report.getVouchersByStand() != null) {
            for (VouchersByStandDTO s : report.getVouchersByStand()) {
                StandResponseDTO stand = s.getStand();

                Paragraph subtitulo = new Paragraph("Puesto " +
                        (stand == null ? "Sin número" : stand.getNumber()))
                        .setBold()
                        .setFontSize(12);
                doc.add(subtitulo);

                Table resumenStand = new Table(3);
                resumenStand.addCell(new Cell().add(new Paragraph("Ventas")).setBold());
                resumenStand.addCell(new Cell().add(new Paragraph("Deuda")).setBold());
                resumenStand.addCell(new Cell().add(new Paragraph("Comprobantes emitidos")).setBold());

                resumenStand.addCell(new Paragraph(n(s.getTotalSales()).toString()));
                resumenStand.addCell(new Paragraph(n(s.getTotalDebt()).toString()));
                resumenStand.addCell(new Paragraph(String.valueOf(s.getTotalIssuedVouchersCount())));
                doc.add(resumenStand);
                doc.add(new Paragraph("\n"));

                if (s.getVouchers() != null && !s.getVouchers().isEmpty()) {
                    Table tablaVouchers = new Table(6);
                    tablaVouchers.addCell(new Cell().add(new Paragraph("N°")).setBold());
                    tablaVouchers.addCell(new Cell().add(new Paragraph("Serie")).setBold());
                    tablaVouchers.addCell(new Cell().add(new Paragraph("Estado")).setBold());
                    tablaVouchers.addCell(new Cell().add(new Paragraph("Fecha emisión")).setBold());
                    tablaVouchers.addCell(new Cell().add(new Paragraph("Monto pagado")).setBold());
                    tablaVouchers.addCell(new Cell().add(new Paragraph("Monto pendiente")).setBold());

                    int contador = 1;
                    for (VoucherResponseDTO v : s.getVouchers()) {
                        tablaVouchers.addCell(new Paragraph(String.valueOf(contador++)));
                        tablaVouchers.addCell(new Paragraph(v.getSerialNumber()));
                        tablaVouchers.addCell(new Paragraph(v.getState().name()));
                        tablaVouchers.addCell(new Paragraph(v.getIssueDate().toString()));
                        tablaVouchers.addCell(new Paragraph(n(v.getPaidAmount()).toString()));
                        tablaVouchers.addCell(new Paragraph(n(v.getPendingAmount()).toString()));
                    }
                    doc.add(tablaVouchers);
                    doc.add(new Paragraph("\n"));
                }
            }
        }
    }

    @Override
    protected String buildFilename(CashClosureReport report) {
        String propietario = report.getOwner() == null ? ""
                : ("-" + sanitizeFilenamePart(report.getOwner().getFullName()));
        return "cierre-caja" + propietario + "-" + report.getCreatedAt();
    }
}
