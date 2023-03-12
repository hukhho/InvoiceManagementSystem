package com.example.invoicemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trader {
    @Basic
    @Column(name = "Name")
    private String name;
    @Basic
    @Column(name = "Acc_number")
    private String accNumber;
    @Basic
    @Column(name = "Banking")
    private String banking;
    @Basic
    @Column(name = "Address")
    private String address;
    @Basic
    @Column(name = "Buyer_id")
    private Integer buyerId;
    @Basic
    @Column(name = "Company_name")
    private String companyName;
    @Basic
    @Column(name = "Tax_code")
    private String taxCode;
    @Basic
    @Column(name = "Phone")
    private String phone;
    @Basic
    @Column(name = "Type")
    private String type;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @OneToMany(mappedBy = "traderByTraderId")
    private Collection<Orders> ordersById;
    @OneToMany(mappedBy = "traderByTraderId")
    private Collection<Seller> sellersById;


}
