package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.model.resume.Resume;
import com.mmf.resumeBuilder.model.resume.ResumeSection;

import java.util.List;

public interface ResumeService {
    Resume findById(int resumeId);

    void save(Resume resume);

    <RS extends ResumeSection> List<RS> fetchSection(Integer resumeId, Class<RS> sectionType);

    void updateSection(ResumeSection updatingSection);

    void deleteSection(ResumeSection deletingSection);

    <RS extends ResumeSection> void addSection(RS resumeSection);
}
