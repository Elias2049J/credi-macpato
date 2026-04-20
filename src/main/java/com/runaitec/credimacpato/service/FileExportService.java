package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.FileExportDTO;
import com.runaitec.credimacpato.dto.FileType;
import com.runaitec.credimacpato.dto.report.Report;

public interface FileExportService {
    FileExportDTO exportReport(FileType fileType, Report report);
}

