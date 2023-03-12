package com.example.invoicemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Files {
    @Basic
    @Column(name = "File_name")
    private String fileName;
    @Basic
    @Column(name = "File_path")
    private String filePath;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Order_id")
    private int orderId;
    @OneToOne
    @JoinColumn(name = "Order_id", referencedColumnName = "Id", nullable = false)
    private Orders ordersByOrderId;

}
