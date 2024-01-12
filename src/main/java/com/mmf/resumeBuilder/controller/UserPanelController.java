package com.mmf.resumeBuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class UserPanelController {
    @GetMapping("")
    public String showUserPanel(Model model){
        return "user-panel";
    }

    @GetMapping("/resumes")
    public String getResumeList(){
        return "resumes";
    }
}
