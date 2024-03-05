package com.mmf.resumeBuilder.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @GetMapping("/home")
    public String loadHomePage(Model model) {
        model.addAttribute("date", new Date());
        return "home";
    }

    @GetMapping("/")
    public String redirectToHomeEndpoint(Model model) {
        model.addAttribute("date", new Date());
        return "redirect:/home";
    }
}
