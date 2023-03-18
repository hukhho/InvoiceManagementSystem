package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.entity.ExcelCell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExcelCellRepository extends JpaRepository<ExcelCell, Long> {
    List<ExcelCell> findAllByTemplateId(Long id);
}