package com.mmf.resumeBuilder.repository;

import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.entity.resume.ResumeSection;

import java.util.List;

public interface ResumeDAO {
    List<Resume> findAll();

    Resume findById(int resumeId);

    void save(Resume resume);

    void delete(Integer resumeId);

    void delete(Resume resume);

    <RS extends ResumeSection> List<RS> fetchSection(Integer resumeId, Class<RS> sectionType);

    void updateSection(ResumeSection updatingSection);

    void deleteSection(ResumeSection deletingSection);

    <RS extends ResumeSection> void addSection(RS resumeSection);

    List<Resume> findAllByUserId(Integer userId);
}