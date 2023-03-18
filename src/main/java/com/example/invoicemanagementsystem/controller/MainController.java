package com.example.invoicemanagementsystem.controller;


import com.example.invoicemanagementsystem.entity.Admin;
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
        Admin admin = (Admin) session.getAttribute("admin");

        if (seller == null && admin == null) {
            return "redirect:/auth/login";
        }
        String username = "";
        if (seller != null ) {
            username = seller.getUsername();
        } else {
            username = admin.getUsername();

        }
        model.addAttribute("username", username);

        return "home";
    }
}
