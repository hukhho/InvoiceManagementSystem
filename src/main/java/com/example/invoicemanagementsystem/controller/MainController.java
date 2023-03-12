package com.example.invoicemanagementsystem.controller;


import com.example.invoicemanagementsystem.entity.Seller;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @RequestMapping("")
    public String home(HttpSession session, Model model) {
        Seller seller = (Seller) session.getAttribute("seller");
        if (seller == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("username", seller.getUsername());
        return "home";
    }
}
