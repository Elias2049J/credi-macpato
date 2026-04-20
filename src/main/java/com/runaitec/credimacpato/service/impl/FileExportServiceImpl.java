package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.FileExportDTO;
import com.runaitec.credimacpato.dto.FileType;
import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.PartnerDebtsReport;
import com.runaitec.credimacpato.dto.report.Report;
import com.runaitec.credimacpato.service.FileExportService;
import com.runaitec.credimacpato.service.report.exporter.cashclosure.CashClosureCsvExporter;
import com.runaitec.credimacpato.service.report.exporter.cashclosure.CashClosureExcelExporter;
import com.runaitec.credimacpato.service.report.exporter.cashclosure.CashClosurePdfExporter;
import com.runaitec.credimacpato.service.report.exporter.partnerdebts.PartnerDebtsCsvExporter;
import com.runaitec.credimacpato.service.report.exporter.partnerdebts.PartnerDebtsExcelExporter;
import com.runaitec.credimacpato.service.report.exporter.partnerdebts.PartnerDebtsPdfExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileExportServiceImpl implements FileExportService {

    private final CashClosureCsvExporter cashClosureCsvExporter;
    private final CashClosurePdfExporter cashClosurePdfExporter;
    private final CashClosureExcelExporter cashClosureExcelExporter;

    private final PartnerDebtsCsvExporter partnerDebtsCsvExporter;
    private final PartnerDebtsPdfExporter partnerDebtsPdfExporter;
    private final PartnerDebtsExcelExporter partnerDebtsExcelExporter;

    @Override
    public FileExportDTO exportReport(FileType fileType, Report report) {
        if (report == null) {
            throw new IllegalArgumentException("Report is required");
        }
        if (fileType == null) {
            throw new IllegalArgumentException("FileType is required");
        }

        if (report instanceof CashClosureReport cashClosureReport) {
            return switch (fileType) {
                case CSV -> cashClosureCsvExporter.export(cashClosureReport);
                case PDF -> cashClosurePdfExporter.export(cashClosureReport);
                case EXCEL -> cashClosureExcelExporter.export(cashClosureReport);
            };
        }

        if (report instanceof PartnerDebtsReport partnerDebtsReport) {
            return switch (fileType) {
                case CSV -> partnerDebtsCsvExporter.export(partnerDebtsReport);
                case PDF -> partnerDebtsPdfExporter.export(partnerDebtsReport);
                case EXCEL -> partnerDebtsExcelExporter.export(partnerDebtsReport);
            };
        }

        throw new IllegalArgumentException("Unsupported report type: " + report.getClass().getName());
    }
}

