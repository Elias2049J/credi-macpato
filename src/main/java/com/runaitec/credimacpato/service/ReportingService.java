package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.report.CashClosureReportRequest;
import com.runaitec.credimacpato.dto.report.CashClosureReport;
import com.runaitec.credimacpato.dto.report.CustomerDebtsReportRequest;
import com.runaitec.credimacpato.dto.report.PartnerDebtsReport;

public interface ReportingService {
    CashClosureReport generateCashClosureReport(CashClosureReportRequest request);
    PartnerDebtsReport generateCustomerDebtsReport(CustomerDebtsReportRequest request);
}
