package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.entity.Payment;
import com.example.invoicemanagementsystem.entity.Seller;
import com.mservice.pay.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);

}