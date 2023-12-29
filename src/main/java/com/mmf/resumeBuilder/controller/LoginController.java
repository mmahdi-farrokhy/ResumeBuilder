package com.mmf.resumeBuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @PostMapping("/login/s")
    public String saySomething(Model model){
        return model.toString();
    }
}
