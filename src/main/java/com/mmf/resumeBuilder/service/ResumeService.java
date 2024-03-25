package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.entity.resume.Resume;

import java.util.List;

public interface ResumeService {
    Resume findResumeById(int resumeId);

    Resume saveResume(Resume resume);

    void deleteResume(Integer resumeId);

    void deleteResume(Resume resume);

    Resume updateResume(Resume resume, Integer resumeId);

    Resume downloadResume(Integer resumeId, ResumeTheme theme);

    List<Resume> findAllResumesByUserEmail(String userEmail);
}
