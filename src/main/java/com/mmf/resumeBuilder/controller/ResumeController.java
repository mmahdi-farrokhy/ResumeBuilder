package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.service.ResumeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resume")
@AllArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("")
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume) {
        return new ResponseEntity<>(resumeService.saveResume(resume), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteResume(@PathVariable int id) {
        resumeService.deleteResume(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/theme")
    public ResponseEntity<Resume> downloadResume(@PathVariable int id, @RequestParam("theme") ResumeTheme theme) {
        return new ResponseEntity<>(resumeService.downloadResume(id, theme), HttpStatus.OK);
    }

    @GetMapping("/all/{email}")
    public ResponseEntity<List<Resume>> findAllResumes(@PathVariable String email) {
        return new ResponseEntity<>(resumeService.findAllResumesByUserEmail(email), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resume> updateResume(@RequestBody Resume resume, @PathVariable int id) {
        return new ResponseEntity<>(resumeService.updateResume(resume, id), HttpStatus.OK);
    }
}
