package com.example.invoicemanagementsystem.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.lang.reflect.Field;


@XmlRootElement(name = "Product")
public class Product {
    private String code;
    private String prodName;
    private double prodPrice;
    private int prodQuantity;
    private int prodType;
    private String prodUnit;
    private int pos;
    private double total;
    private double amount;
    private int isDiscountRow;

    public Product() {}

    public Product(String code, String prodName, double prodPrice, int prodQuantity, int prodType, String prodUnit, int pos, double total, double amount, int isDiscountRow) {
        this.code = code;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodQuantity = prodQuantity;
        this.prodType = prodType;
        this.prodUnit = prodUnit;
        this.pos = pos;
        this.total = total;
        this.amount = amount;
        this.isDiscountRow = isDiscountRow;
    }

    @XmlElement(name = "Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "ProdName")
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @XmlElement(name = "ProdPrice")
    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    @XmlElement(name = "ProdQuantity")
    public int getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(int prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    @XmlElement(name = "ProdType")
    public int getProdType() {
        return prodType;
    }

    public void setProdType(int prodType) {
        this.prodType = prodType;
    }

    @XmlElement(name = "ProdUnit")
    public String getProdUnit() {
        return prodUnit;
    }

    public void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit;
    }

    @XmlElement(name = "Pos")
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @XmlElement(name = "Total")
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @XmlElement(name = "Amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @XmlElement(name = "IsDiscountRow")
    public int getIsDiscountRow() {
        return isDiscountRow;
    }

    public void setIsDiscountRow(int isDiscountRow) {
        this.isDiscountRow = isDiscountRow;
    }



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
