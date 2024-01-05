package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.model.User;
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
        model.addAttribute("user", new User());
        return "login-page";
    }

    @GetMapping("/successful")
    public String login(@ModelAttribute User user) {
        if (userService.existsByEmail(user.getEmail())) {
            if (userService.findByEmail(user.getEmail()).getPassword().equals(user.getPassword())) {
                return "redirect:/";
            }
        }

        return "redirect:/login/error";
    }

    @GetMapping("/error")
    public String showErrorPage(){
        return "login-error";
    }
}
