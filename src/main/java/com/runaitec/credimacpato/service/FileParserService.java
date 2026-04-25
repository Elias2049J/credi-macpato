package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.debt.BulkDebtUploadRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileParserService {
    BulkDebtUploadRequestDTO parseExcelToDTO(MultipartFile file);

    BulkDebtUploadRequestDTO parseCsvToDTO(MultipartFile file);
}
