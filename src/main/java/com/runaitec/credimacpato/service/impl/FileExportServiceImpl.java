package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.FileExportDTO;
import com.runaitec.credimacpato.dto.FileType;
import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.CustomerDebtsReport;
import com.runaitec.credimacpato.dto.report.Report;
import com.runaitec.credimacpato.service.FileExportService;
import com.runaitec.credimacpato.service.report.exporter.cashclosure.CashClosureCsvExporter;
import com.runaitec.credimacpato.service.report.exporter.cashclosure.CashClosureExcelExporter;
import com.runaitec.credimacpato.service.report.exporter.cashclosure.CashClosurePdfExporter;
import com.runaitec.credimacpato.service.report.exporter.customerdebts.CustomerDebtsCsvExporter;
import com.runaitec.credimacpato.service.report.exporter.customerdebts.CustomerDebtsExcelExporter;
import com.runaitec.credimacpato.service.report.exporter.customerdebts.CustomerDebtsPdfExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileExportServiceImpl implements FileExportService {

    private final CashClosureCsvExporter cashClosureCsvExporter;
    private final CashClosurePdfExporter cashClosurePdfExporter;
    private final CashClosureExcelExporter cashClosureExcelExporter;

    private final CustomerDebtsCsvExporter customerDebtsCsvExporter;
    private final CustomerDebtsPdfExporter customerDebtsPdfExporter;
    private final CustomerDebtsExcelExporter customerDebtsExcelExporter;

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

        if (report instanceof CustomerDebtsReport customerDebtsReport) {
            return switch (fileType) {
                case CSV -> customerDebtsCsvExporter.export(customerDebtsReport);
                case PDF -> customerDebtsPdfExporter.export(customerDebtsReport);
                case EXCEL -> customerDebtsExcelExporter.export(customerDebtsReport);
            };
        }

        throw new IllegalArgumentException("Unsupported report type: " + report.getClass().getName());
    }
}

