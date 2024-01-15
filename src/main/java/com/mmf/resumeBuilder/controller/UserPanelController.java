package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.model.AppUser;
import com.mmf.resumeBuilder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel")
public class UserPanelController {
    UserService userService;

    @Autowired
    public UserPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showUserPanel(@PathVariable String id, Model model) {
        AppUser appUser = userService.fetchUser(Integer.valueOf(id));
        model.addAttribute("user", appUser);
        return "user-panel";
    }

    @GetMapping("/delete/{resumeid}")
    public String goToDeleteResumeEndpoint(@PathVariable String resumeid) {
        return "redirect:/resume/delete/" + resumeid;
    }

    @GetMapping("/download/{resumeid}")
    public String goToDownloadResumeEndpoint(@PathVariable String resumeid) {
        return "redirect:/resume/download/" + resumeid;
    }

    @GetMapping("/edit/{resumeid}")
    public String goToEditResumeEndpoint(@PathVariable String resumeid) {
        return "redirect:/resume/edit/" + resumeid;
    }

    @GetMapping("/share/{resumeid}")
    public String goToShareResumeEndpoint(@PathVariable String resumeid) {
        return "redirect:/resume/share/" + resumeid;
    }

    @GetMapping("/{userid}/profile")
    public String showUserProfile(@PathVariable String userid, Model model) {
        AppUser appUser = userService.fetchUser(Integer.valueOf(userid));
        model.addAttribute("user", appUser);
        return "user-profile";
    }

    @PostMapping("/profile/update")
    public String updateUser(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-profile";
        } else {
            user.setRole(UserRole.User);
            userService.saveUser(user);
            model.addAttribute("user", user);
            return "profile-update-success";
        }
    }
}
