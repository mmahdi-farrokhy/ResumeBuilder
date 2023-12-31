package com.mmf.resumeBuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class HomeController {

    @GetMapping("/")
    public String loadHomePage(Model model) {
        model.addAttribute("date", new Date());
        return "home-page";
    }
}
