package com.mmf.resumeBuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {
    @PostMapping("/signup/create-user")
    public String createUser(Model model) {
        return model.toString();
    }
}
