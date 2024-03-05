package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.entity.AppUser;
import com.mmf.resumeBuilder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new AppUser());
        return "login";
    }

    @GetMapping("/proceed")
    public String login(@ModelAttribute AppUser user) {
        if (userService.existsByEmail(user.getEmail())) {
            if (userService.findByEmail(user.getEmail()).getPassword().equals(user.getPassword())) {
                return "redirect:/login/success";
            }
        }

        return "redirect:/login/error";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "login-success";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "login-error";
    }
}
