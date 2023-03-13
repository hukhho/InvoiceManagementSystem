package com.example.invoicemanagementsystem.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.AbstractDocument;

@XmlRootElement(name = "Invoice")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Invoice {

    @XmlElement(name = "Content")
    private Content content;

    @XmlElement(name = "RowPerPage")
    private String rowPerPage;

    @XmlElement(name = "qrCodeData")
    private String qrCodeData;


    // getters and setters

}