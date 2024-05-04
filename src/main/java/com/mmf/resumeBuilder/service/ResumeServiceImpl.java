package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.exception.InvalidResumeException;
import com.mmf.resumeBuilder.exception.ResumeNotFoundException;
import com.mmf.resumeBuilder.exception.UserNotFoundException;
import com.mmf.resumeBuilder.repository.ResumeRepository;
import com.mmf.resumeBuilder.service.tools.word.documentgenerator.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    @Override
    public Resume findResumeById(int resumeId) {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        if (resume.isEmpty()) {
            throw new ResumeNotFoundException(resumeId);
        }

        return resume.get();
    }

    @Override
    public Resume saveResume(Resume resume) {
        if (resume.getUser() == null) {
            throw new InvalidResumeException();
        }

        return resumeRepository.save(resume);
    }

    @Override
    public void deleteResume(Integer resumeId) {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        if (resume.isEmpty()) {
            throw new ResumeNotFoundException(resumeId);
        }

        resumeRepository.delete(resume.get());
    }

    @Override
    public void deleteResume(Resume resume) {
        if (!resumeRepository.existsById(resume.getId())) {
            throw new ResumeNotFoundException(resume.getId());
        }

        resumeRepository.delete(resume);
    }

    @Override
    public Resume updateResume(Resume resume, Integer resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new ResumeNotFoundException(resumeId);
        }

        return resumeRepository.save(resume);
    }

    @Override
    public Resume downloadResume(Integer resumeId, ResumeTheme theme) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isEmpty()) {
            throw new ResumeNotFoundException(resumeId);
        }

        Resume resume = optionalResume.get();
        createDocumentGenerator(theme).generateWordDocument(resume);
        return resume;
    }

    private DocumentGenerator createDocumentGenerator(ResumeTheme theme) {
        DocumentGenerator documentGenerator = null;

        switch (theme) {
            case ATSClassic -> documentGenerator = new ATSClassicDocumentGenerator();
            case Classic_Accounting -> documentGenerator = new ClassicAccountingDocumentGenerator();
            case Simple_Florist -> documentGenerator = new SimpleFloristDocumentGenerator();
            case Woodworking -> documentGenerator = new WoodworkingDocumentGenerator();
        }

        return documentGenerator;
    }

    @Override
    public List<Resume> findAllResumesByUserEmail(String userEmail) {
        List<Resume> allResumes = resumeRepository.findAllResumesByUserEmail(userEmail);
        if (allResumes.isEmpty()) {
            throw new UserNotFoundException(userEmail);
        }

        return allResumes;
    }
}
