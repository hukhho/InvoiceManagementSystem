package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.entity.Admin;
import com.example.invoicemanagementsystem.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);

}