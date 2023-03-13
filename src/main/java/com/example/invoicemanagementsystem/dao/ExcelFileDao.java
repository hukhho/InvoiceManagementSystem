package com.example.invoicemanagementsystem.dao;

import com.example.invoicemanagementsystem.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExcelFileDao {
    List<Files> getAllFiles();
    //void addFile(Files excelFile);
    void addFile(MultipartFile file) throws IOException;
    Files getFileById(int id);
    boolean deleteFile(int id);
}