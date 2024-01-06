package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.data.dao.ResumeDAO;
import com.mmf.resumeBuilder.model.resume.Resume;
import com.mmf.resumeBuilder.model.resume.ResumeSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {
    private ResumeDAO resumeDAO;

    @Autowired
    public ResumeServiceImpl(ResumeDAO resumeDAO) {
        this.resumeDAO = resumeDAO;
    }

    @Override
    public Resume findById(int resumeId) {
        return resumeDAO.findById(resumeId);
    }

    @Override
    public void save(Resume resume) {
        resumeDAO.save(resume);
    }

    @Override
    public <RS extends ResumeSection> List<RS> fetchSection(Integer resumeId, Class<RS> sectionType) {
        return resumeDAO.fetchSection(resumeId, sectionType);
    }

    @Override
    public void updateSection(ResumeSection updatingSection) {
        resumeDAO.updateSection(updatingSection);
    }

    @Override
    public void deleteSection(ResumeSection deletingSection) {
        resumeDAO.deleteSection(deletingSection);
    }

    @Override
    public <RS extends ResumeSection> void addSection(RS resumeSection) {
        resumeDAO.addSection(resumeSection);
    }
}
