package com.example.invoicemanagementsystem.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Content {
    @XmlElement(name = "Key")
    private String key;

    @XmlElement(name = "ArisingDate")
    private String arisingDate;

    @XmlElement(name = "ComFax")
    private String comFax;

    @XmlElement(name = "ComName")
    private String comName;

    @XmlElement(name = "ComTaxCode")
    private String comTaxCode;

    @XmlElement(name = "ComAddress")
    private String comAddress;

    @XmlElement(name = "ComPhone")
    private String comPhone;

    @XmlElement(name = "ComEmail")
    private String comEmail;

    @XmlElement(name = "ComBankNo")
    private String comBankNo;

    @XmlElement(name = "ComBankName")
    private String comBankName;

    @XmlElement(name = "Ikey")
    private String ikey;

    @XmlElement(name = "ParentName")
    private String parentName;

    @XmlElement(name = "InvoiceName")
    private String invoiceName;

    @XmlElement(name = "InvoicePattern")
    private String invoicePattern;

    @XmlElement(name = "SerialNo")
    private String serialNo;

    @XmlElement(name = "InvoiceNo")
    private String invoiceNo;

    @XmlElement(name = "PaymentMethod")
    private String paymentMethod;

    @XmlElement(name = "CusCode")
    private String cusCode;

    @XmlElement(name = "CusName")
    private String cusName;

    @XmlElement(name = "CusTaxCode")
    private String cusTaxCode;

    @XmlElement(name = "CusPhone")
    private String cusPhone;

    @XmlElement(name = "CusAddress")
    private String cusAddress;

    @XmlElement(name = "CusBankName")
    private String cusBankName;

    @XmlElement(name = "CusBankNo")
    private String cusBankNo;

    @XmlElement(name = "Total")
    private String total;

    @XmlElement(name = "VATAmount")
    private String vatAmount;

    @XmlElement(name = "Amount")
    private String amount;

    @XmlElement(name = "AmountInWords")
    private String amountInWords;

    @XmlElement(name = "Buyer")
    private String buyer;

    @XmlElement(name = "VATRate")
    private String vatRate;

    @XmlElement(name = "Note")
    private String note;

    @XmlElement(name = "CusEmails")
    private String cusEmails;

    @XmlElement(name = "amountInWordsL2")
    private String amountInWordsL2;

    @XmlElementWrapper(name = "Products")
    @XmlElement(name = "Product")
    private List<Product> products;

    @XmlElement(name = "GrossValue")
    private String grossValue;

    @XmlElement(name = "GrossValue0")
    private String grossValue0;

    @XmlElement(name = "VatAmount0")
    private String vatAmount0;

    @XmlElement(name = "GrossValue5")
    private String grossValue5;

    @XmlElement(name = "VatAmount5")
    private String vatAmount5;

    @XmlElement(name = "GrossValue10")
    private String grossValue10;

    @XmlElement(name = "VatAmount10")
    private String vatAmount10;

    @XmlElement(name = "GrossValueNDeclared")
    private String grossValueNDeclared;

    @XmlElement(name = "VatAmountNDeclared")
    private String vatAmountNDeclared;

    @XmlElement(name = "ExchangeRate")
    private String exchangeRate;

    @XmlElement(name = "CurrencyUnit")
    private String currencyUnit;

    @XmlElement(name = "PortalLink")
    private String portalLink;

    @XmlElement(name = "Hidden")
    private String hidden;

    @XmlElement(name = "SignDate")
    private String signDate;
    // getters and setters

    public String getCellValue(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

}