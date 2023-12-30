package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.entities.User;
import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.service.ResumeService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {
    ResumeService resumeService;

    @Autowired
    public SignupController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/signup/create-user")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "signup-page";
        else {
            user.setRole(UserRole.User);
            resumeService.saveUser(user);
            return "signup-conformation-page";
        }
    }
}
