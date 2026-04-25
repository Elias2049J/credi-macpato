package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.FileType;
import com.runaitec.credimacpato.dto.debt.BulkDebtUploadResponseDTO;
import com.runaitec.credimacpato.service.DebtImportService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/debts")
@RequiredArgsConstructor
public class DebtController {

    private final DebtImportService debtImportService;

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BulkDebtUploadResponseDTO> bulkImport(
            @RequestPart("file") MultipartFile file,
            @NotNull @RequestPart("fileType") FileType fileType) {
        return ResponseEntity.ok(debtImportService.importDebts(file, fileType));
    }
}
