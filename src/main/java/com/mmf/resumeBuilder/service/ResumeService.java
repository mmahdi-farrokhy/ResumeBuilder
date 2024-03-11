package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.entity.resume.ResumeSection;

import java.util.List;

public interface ResumeService {
    Resume findResumeById(int resumeId);

    Resume saveResume(Resume resume);

    void deleteResume(Integer resumeId);

    void deleteResume(Resume resume);

    Resume updateResume(Resume resume, Integer resumeId);

    Resume downloadResume(Integer resumeId);

    List<Resume> findAllResumesByUserEmail(String userEmail);
}
