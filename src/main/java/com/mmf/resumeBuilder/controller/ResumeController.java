package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.model.AppUser;
import com.mmf.resumeBuilder.model.resume.Resume;
import com.mmf.resumeBuilder.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/resume")
public class ResumeController {
    ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("")
    public String showResumePage(@ModelAttribute AppUser user, Model model) {
        model.addAttribute("resumes", resumeService.findAllByUserId(user.getId()));
        return "resume";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteResume(@PathVariable int id) {
        resumeService.deleteResume(id);
        return "redirect:/resume/delete/success";
    }

    @GetMapping("/delete/success")
    public String showResumeDeleteSuccessPage() {
        return "resume-delete-success";
    }

    @GetMapping("/download/{id}")
    public String downloadResume(@PathVariable int id) {
        resumeService.downloadResume(id);
        return "redirect:/resume/download/success";
    }

    @GetMapping("/download/success")
    public String showResumeDownloadSuccessPage() {
        return "resume-download-success";
    }

    @PostMapping("/edit/{id}")
    public String editResume(@Valid @ModelAttribute("resume") Resume resume, @PathVariable int id, Model model) {
        Resume byId = resumeService.findById(id);
        byId = resume;
        byId.setId(id);
        resumeService.save(byId);
        return "redirect:/resume/edit/success";
    }

    @GetMapping("/edit/success")
    public String showResumeEditSuccessPage() {
        return "resume-edit-success";
    }
}
