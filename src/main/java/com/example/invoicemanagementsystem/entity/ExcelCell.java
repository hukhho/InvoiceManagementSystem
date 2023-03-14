package com.example.invoicemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "excel_cell")
public class ExcelCell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private ExcelTemplate template;

    @Column(name = "cell_reference")
    private String cellReference;

    @Column(name = "cell_value")
    private String cellValue;

    // getters and setters
}