package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.*;

import java.util.List;

public interface ResumeDAO {
    Resume findById(int resumeId);

    void save(Resume resume);

    <RS extends ResumeSection> List<RS> fetchSection(Integer resumeId, Class<RS> sectionType);

    void updateSection(ResumeSection updatingSection);

    void deleteSection(ResumeSection deletingSection);

    <RS extends ResumeSection> void addSection(RS resumeSection);
}
