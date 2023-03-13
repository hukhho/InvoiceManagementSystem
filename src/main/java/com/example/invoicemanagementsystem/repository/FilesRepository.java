package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<Files, Long> {
}