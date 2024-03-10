package com.mmf.resumeBuilder.repository;

import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.entity.resume.ResumeSection;

import java.util.List;

public interface ResumeRepository {
    List<Resume> findAllResumes();

    Resume findResumeById(int resumeId);

    Resume saveResume(Resume resume);

    void deleteResume(Integer resumeId);

    void deleteResume(Resume resume);

    <RS extends ResumeSection> List<RS> findResumeSection(Integer resumeId, Class<RS> sectionType);

    void updateResumeSection(ResumeSection updatingSection);

    void deleteResumeSection(ResumeSection deletingSection);

    <RS extends ResumeSection> void addResumeSection(RS resumeSection);

    List<Resume> findAllResumesByUserEmail(String userEmail);
}
