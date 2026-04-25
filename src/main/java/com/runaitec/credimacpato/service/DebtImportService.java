package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.FileType;
import com.runaitec.credimacpato.dto.debt.BulkDebtUploadResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DebtImportService {
    BulkDebtUploadResponseDTO importDebts(MultipartFile file, FileType fileType);
}
