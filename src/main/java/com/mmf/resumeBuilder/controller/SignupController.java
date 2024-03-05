package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.entity.AppUser;
import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignupController {
    UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new AppUser());
        return "signup";
    }

    @PostMapping("/proceed")
    public String createUser(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        } else {
            user.setRole(UserRole.User);
            userService.saveUser(user);
            model.addAttribute("user", user);
            return "signup-success";
        }
    }
}
