package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.entity.Admin;
import com.example.invoicemanagementsystem.entity.Seller;
import com.example.invoicemanagementsystem.entity.Users;
import com.example.invoicemanagementsystem.repository.AdminRepository;
import com.example.invoicemanagementsystem.repository.SellerRepository;
import com.example.invoicemanagementsystem.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping(value = "/auth")
public class LoginController{
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UsersRepository usersRepository;

    @RequestMapping(value = "/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, Model model) {

//        Optional<Seller> optionalUser = sellerRepository.findByUsername(username);
//        Optional<Admin> optionalAdmin = adminRepository.findByUsername(username);
//
//        if (optionalUser.isPresent()) {
//            Seller seller = optionalUser.get();
//            if (password.equals(seller.getPassword())) {
//                session.setAttribute("seller", seller);
//                return "redirect:/home";
//            }
//        }
//
//        if (optionalAdmin.isPresent()) {
//            Admin admin = optionalAdmin.get();
//            if (password.equals(admin.getPassword())) {
//                session.setAttribute("admin", admin);
//                return "redirect:/home";
//            }
//        }

        Optional<Users> optionalUsers = usersRepository.findByUsername(username);

        if (optionalUsers.isPresent()) {
            Users user = optionalUsers.get();
            if (password.equals(user.getPassword())) {
                session.setAttribute("user", user);
                return "redirect:/home";
            }
        }

        model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");

        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/auth/login";
    }
}
