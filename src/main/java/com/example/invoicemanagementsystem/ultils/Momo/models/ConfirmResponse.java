package com.example.invoicemanagementsystem.ultils.Momo.models;


import com.example.invoicemanagementsystem.ultils.Momo.enums.ConfirmRequestType;

public class ConfirmResponse extends Response {
    private Long amount;
    private Long transId;
    private String requestId;
    private ConfirmRequestType requestType;
}
