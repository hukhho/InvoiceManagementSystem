package com.example.invoicemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    @Basic
    @Column(name = "Signature")
    private String signature;
    @Basic
    @Column(name = "Username")
    private String username;
    @Basic
    @Column(name = "Password")
    private String password;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "Trader_id", insertable=false, updatable=false)
    private int traderId;
    @ManyToOne
    @JoinColumn(name = "Trader_id", referencedColumnName = "Id", nullable = false)
    private Trader traderByTraderId;

}
