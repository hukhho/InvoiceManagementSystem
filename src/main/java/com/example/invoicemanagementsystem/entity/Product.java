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
public class Product {
    @Basic
    @Column(name = "Name")
    private String name;
    @Basic
    @Column(name = "Price")
    private int price;
    @Basic
    @Column(name = "Unit")
    private int unit;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @OneToMany(mappedBy = "productByProductId")
    private Collection<OrderDetail> orderDetailsById;


}
