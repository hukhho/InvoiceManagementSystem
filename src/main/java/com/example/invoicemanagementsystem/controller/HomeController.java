package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.entity.Admin;
import com.example.invoicemanagementsystem.entity.ExcelTemplate;
import com.example.invoicemanagementsystem.entity.Seller;
import com.example.invoicemanagementsystem.entity.Users;
import com.example.invoicemanagementsystem.service.ExcelFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
    @Autowired
    ExcelFileService excelFileService;

    @RequestMapping("")
    public String home(HttpSession session, Model model) {
//        Seller seller = (Seller) session.getAttribute("seller");
//        Admin admin = (Admin) session.getAttribute("admin");
//
//        if (seller == null && admin == null) {
//            return "redirect:/auth/login";
//        }
//        String username = "";
//        if (seller != null ) {
//            username = seller.getUsername();
//        } else {
//            username = admin.getUsername();
//
//        }
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return "redirect:/auth/login";
        }
        String username = user.getUsername();



        model.addAttribute("excelFiles", excelFileService.getAllExcelFiles());
        model.addAttribute("username", username);

        return "home";
    }

}
