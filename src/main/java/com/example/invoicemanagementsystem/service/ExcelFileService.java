package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.entity.ExcelTemplate;

import java.util.List;
import java.util.Optional;

public interface ExcelFileService {
    List<ExcelTemplate> getAllExcelFiles();

    Optional<ExcelTemplate> getExcelFileById(Long id);

    ExcelTemplate createExcelFile(ExcelTemplate excelFile);

    ExcelTemplate updateExcelFile(ExcelTemplate excelFile);

    void deleteExcelFileById(Long id);
}
