package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.report.CashClosureReportRequestDTO;
import com.runaitec.credimacpato.dto.report.CashClosureReportResponseDTO;
import com.runaitec.credimacpato.dto.report.PartnerDebtsReportRequestDTO;
import com.runaitec.credimacpato.dto.report.PartnerDebtsReportResponseDTO;

public interface ReportingService {
    CashClosureReportResponseDTO generateCashClosureReport(CashClosureReportRequestDTO request);
    PartnerDebtsReportResponseDTO generatePartnerDebtsReport(PartnerDebtsReportRequestDTO request);
}
