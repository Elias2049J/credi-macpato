package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.report.CashClosureReportRequestDTO;
import com.runaitec.credimacpato.dto.report.CashClosureReportResponseDTO;
import com.runaitec.credimacpato.dto.report.PartnerDebtsReportRequestDTO;
import com.runaitec.credimacpato.dto.report.PartnerDebtsReportResponseDTO;
import com.runaitec.credimacpato.service.ReportingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportingService reportingService;

    @PostMapping("/cash-closure")
    public ResponseEntity<CashClosureReportResponseDTO> cashClosure(@Valid @RequestBody CashClosureReportRequestDTO request) {
        return ResponseEntity.ok(reportingService.generateCashClosureReport(request));
    }

    @PostMapping("/partner-debts")
    public ResponseEntity<PartnerDebtsReportResponseDTO> partnerDebts(@Valid @RequestBody PartnerDebtsReportRequestDTO request) {
        return ResponseEntity.ok(reportingService.generatePartnerDebtsReport(request));
    }
}
