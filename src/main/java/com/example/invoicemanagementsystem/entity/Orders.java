package com.example.invoicemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Basic
    @Column(name = "Order_day")
    private Date orderDay;
    @Basic
    @Column(name = "Total")
    private int total;
    @Basic
    @Column(name = "Status")
    private String status;
    @Basic
    @Column(name = "Method")
    private String method;
    @Basic
    @Column(name = "Serial")
    private int serial;
    @Basic
    @Column(name = "Invoice_no")
    private int invoiceNo;
    @Basic
    @Column(name = "Name")
    private String name;
    @Basic
    @Column(name = "Amount_in_words")
    private String amountInWords;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "Trader_id", insertable=false, updatable=false)
    private int traderId;
    @OneToOne(mappedBy = "ordersByOrderId")
    private Files filesById;
    @OneToMany(mappedBy = "ordersByOrderId")
    private Collection<OrderDetail> orderDetailsById;
    @ManyToOne
    @JoinColumn(name = "Trader_id", referencedColumnName = "Id", nullable = false)
    private Trader traderByTraderId;

}
