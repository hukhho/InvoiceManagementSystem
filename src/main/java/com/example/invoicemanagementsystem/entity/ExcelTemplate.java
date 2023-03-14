package com.example.invoicemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "excel_template")
public class ExcelTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "template_file", columnDefinition="VARBINARY(MAX)")
    private byte[] templateFile;

    @Column(name = "upload_date")
    private Date uploadDate;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExcelCell> cells = new ArrayList<>();

    // getters and setters
}