package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.entity.Users;
import com.example.invoicemanagementsystem.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        List<Users> users = usersRepository.findAll();
        model.addAttribute("user", new Users());

        return "signup";
    }

    @PostMapping("/signup")
    public String processSignupForm(@ModelAttribute("user") @Valid Users user, BindingResult result, @RequestParam  ("confirmPassword") String confirmPassword, Model model) {
        // Check if the password and confirmation match
        if (!user.getPassword().equals(confirmPassword)) {
            result.rejectValue("password", "error.user", "Passwords do not match");
        }

        // Check for validation errors
        if (result.hasErrors()) {
            return "signup";
        }

        // Save the user to the database
        try {
            user.setAdmin(false);
            user.setAmount(0L);
            usersRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("username", "error.user", "Username or email already exists");
            return "signup";
        }

        return "redirect:/auth/login?signupSuccess=true";
    }

    @GetMapping("/edit-profile")
    public String showEditProfileForm(HttpSession session, Model model, HttpServletRequest request) {
        Users user = (Users) session.getAttribute("user");

        // Check for flash attribute "errorMess"
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            String errorMess = (String) flashMap.get("errorMess");
            System.out.println(errorMess);
            model.addAttribute("errorMess", errorMess);
        }

        // Redirect to login if user is not logged in
        if (user == null) {
            return "redirect:/auth/login";
        }

        // Get user from repository and add to model
        user = usersRepository.findByUsername(user.getUsername().trim()).orElseThrow();
        model.addAttribute("user", user);

        return "edit-profile";
    }


    @PostMapping("/edit-profile")
    public String updateProfile(jakarta.servlet.http.HttpSession session, @ModelAttribute("user") @Valid Users user, BindingResult result,
                                @RequestParam  (value = "changePassword", required = false) String changePassword,
                                @RequestParam  (value = "oldPassword", required = false) String oldPassword,
                                @RequestParam  (value = "confirmPassword", required = false) String confirmPassword,
                                RedirectAttributes redirectAttributes) {
        Users userLogin = (Users) session.getAttribute("user");

        if (userLogin == null) {
            return "redirect:/auth/login";
        }

//        if (result.hasErrors()) {
//            return "edit-profile";
//        }

        boolean isChangePass = false;
        if (changePassword != null &&  changePassword.trim().equals("on")) {
            isChangePass = true;
        }

        Users userSave = usersRepository.findByUsername(userLogin.getUsername().trim()).orElseThrow();

        if (isChangePass) {
            if (changePassword.trim().equals("on") &&  oldPassword != null && !oldPassword.trim().equals("") && user.getPassword() != null && !user.getPassword().trim().equals("")) {
                if (!userSave.getPassword().equals(oldPassword)) {
                    redirectAttributes.addFlashAttribute("errorMess", "Old pass not true.");
                    return "redirect:/edit-profile";                }
                else if (!user.isPasswordValid(user.getPassword())) {
                    redirectAttributes.addFlashAttribute("errorMess", "Password not valid.");
                    return "redirect:/edit-profile";                }
                else if (!user.getPassword().equals(confirmPassword)) {
                    redirectAttributes.addFlashAttribute("errorMess", "Password not match.");
                    return "redirect:/edit-profile";
                }  else {
                    userSave.setPassword(user.getPassword().trim());
                }
            }
        }

        try {
            userSave.setFullName(user.getFullName().trim());
            userSave.setEmail(user.getEmail().trim());

            usersRepository.save(userSave);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("username", "error.user", "Email already exists");
            return "edit-profile";
        }

        return "redirect:/edit-profile";
    }
}
