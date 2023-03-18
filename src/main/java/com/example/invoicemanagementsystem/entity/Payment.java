package com.example.invoicemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_Id")
    private String orderId;

    @Column(name="isDone")
    private boolean isDone;

    @Column(name="isSuccess")
    private boolean isSuccess;

    @Column(name="amount")
    private Long amount;

    @Column(name="transId")
    private String transId;

    @Column(name="responseTime")
    private String responseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

}
