package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.model.AppUser;
import com.mmf.resumeBuilder.model.resume.Resume;
import com.mmf.resumeBuilder.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete")
    public String deleteResume(@ModelAttribute Resume resume) {
        resumeService.delete(resume);
        return "redirect:/resume/delete/success";
    }

    @GetMapping("/delete/success")
    public String showResumeDeleteSuccessPage() {
        return "resume-delete-success";
    }

    @GetMapping("/download")
    public String downloadResume(@ModelAttribute Resume resume) {
        resumeService.downloadResume(resume);
        return "redirect:/resume/download/success";
    }

    @GetMapping("/download/success")
    public String showResumeDownloadSuccessPage() {
        return "resume-download-success";
    }

    @PostMapping("/edit")
    public String editResume(@Valid @ModelAttribute("resume") Resume resume) {
        resumeService.save(resume);
        return "redirect:/resume/edit/success";
    }

    @GetMapping("/edit/success")
    public String showResumeEditSuccessPage() {
        return "resume-edit-success";
    }

    @GetMapping("/share")
    public String shareResume(@Valid @ModelAttribute("resume") Resume resume) {
        resumeService.share(resume);
        return "redirect:/resume/share/success";
    }

    @GetMapping("/share/success")
    public String showResumeShareSuccessPage() {
        return "resume-share-success";
    }
}
