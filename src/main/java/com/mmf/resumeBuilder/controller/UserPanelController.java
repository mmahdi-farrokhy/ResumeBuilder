package com.mmf.resumeBuilder.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class UserPanelController {
    @GetMapping("user-panel")
    public String showUserPanel() {
        return "userPanel";
    }
}
