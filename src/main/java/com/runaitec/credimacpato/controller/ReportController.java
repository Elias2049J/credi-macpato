package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.FileExportDTO;
import com.runaitec.credimacpato.dto.FileType;
import com.runaitec.credimacpato.dto.report.*;
import com.runaitec.credimacpato.service.FileExportService;
import com.runaitec.credimacpato.service.ReportingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportingService reportingService;
    private final FileExportService fileExportService;

    @PostMapping("/cash-closure")
    public ResponseEntity<Report> cashClosure(@Valid @RequestBody CashClosureReportRequest request) {
        return ResponseEntity.ok(reportingService.generateCashClosureReport(request));
    }

    @PostMapping("/partner-debts")
    public ResponseEntity<Report> partnerDebts(@Valid @RequestBody CustomerDebtsReportRequest request) {
        return ResponseEntity.ok(reportingService.generatePartnerDebtsReport(request));
    }

    @PostMapping(value = "/cash-closure/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportCashClosure(
            @RequestParam FileType fileType,
            @Valid @RequestBody CashClosureReportRequest request
    ) {
        Report report = reportingService.generateCashClosureReport(request);
        FileExportDTO exported = fileExportService.exportReport(fileType, report);
        return buildFileResponse(exported);
    }

    @PostMapping(value = "/partner-debts/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportPartnerDebts(
            @RequestParam FileType fileType,
            @Valid @RequestBody CustomerDebtsReportRequest request
    ) {
        Report report = reportingService.generatePartnerDebtsReport(request);
        FileExportDTO exported = fileExportService.exportReport(fileType, report);
        return buildFileResponse(exported);
    }

    private ResponseEntity<byte[]> buildFileResponse(FileExportDTO exported) {
        MediaType mediaType = exported.getMimetype() == null
                ? MediaType.APPLICATION_OCTET_STREAM
                : MediaType.parseMediaType(exported.getMimetype());

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exported.getFilename() + "\"")
                .body(exported.getData());
    }
}
