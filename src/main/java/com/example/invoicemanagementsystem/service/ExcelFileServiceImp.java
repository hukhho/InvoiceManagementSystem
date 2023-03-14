package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.entity.ExcelTemplate;
import com.example.invoicemanagementsystem.repository.ExcelTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExcelFileServiceImp implements ExcelFileService {
    @Autowired
    private ExcelTemplateRepository excelFileRepository;

    @Override
    public List<ExcelTemplate> getAllExcelFiles() {
        return excelFileRepository.findAll();
    }

    @Override
    public Optional<ExcelTemplate> getExcelFileById(Long id) {
        return excelFileRepository.findById(id);
    }

    @Override
    public ExcelTemplate createExcelFile(ExcelTemplate excelFile) {
        return excelFileRepository.save(excelFile);
    }

    @Override
    public ExcelTemplate updateExcelFile(ExcelTemplate excelFile) {
        return excelFileRepository.save(excelFile);
    }

    @Override
    public void deleteExcelFileById(Long id) {
        excelFileRepository.deleteById(id);
    }
}
