package com.example.invoicemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @RequestMapping(value = "/")
    public String showLoginForm() {
        return "admin";
    }
}
