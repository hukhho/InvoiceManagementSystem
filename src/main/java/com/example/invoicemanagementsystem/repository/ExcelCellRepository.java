package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.entity.ExcelCell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelCellRepository extends JpaRepository<ExcelCell, Long> {
}