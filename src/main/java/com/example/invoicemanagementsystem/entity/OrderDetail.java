package com.example.invoicemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Order_Detail", schema = "dbo", catalog = "invoice_management")
public class OrderDetail {
    @Basic
    @Column(name = "Quantity")
    private int quantity;
    @Basic
    @Column(name = "Order_id", insertable=false, updatable=false)
    private int orderId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "Product_id", insertable=false, updatable=false)
    private int productId;
    @ManyToOne
    @JoinColumn(name = "Order_id", referencedColumnName = "Id", nullable = false)
    private Orders ordersByOrderId;
    @ManyToOne
    @JoinColumn(name = "Product_id", referencedColumnName = "Id", nullable = false)
    private Product productByProductId;

}
