package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.exception.InvalidResumeException;
import com.mmf.resumeBuilder.exception.ResumeNotFoundException;
import com.mmf.resumeBuilder.exception.UserNotFoundException;
import com.mmf.resumeBuilder.repository.ResumeJPARepository;
import com.mmf.resumeBuilder.repository.ResumeRepository;
import com.mmf.resumeBuilder.service.wordtools.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final ResumeJPARepository resumeJPARepository;

    @Override
    public Resume findResumeById(int resumeId) {
        Optional<Resume> resume = resumeJPARepository.findById(resumeId);
        if (resume.isEmpty()) {
            throw new ResumeNotFoundException(resumeId);
        }

        return resume.get();
//        return resumeRepository.findResumeById(resumeId);
    }

    @Override
    public Resume saveResume(Resume resume) {
        if (resume.getUser() == null) {
            throw new InvalidResumeException();
        }

        return resumeJPARepository.save(resume);
//        return resumeRepository.saveResume(resume);
    }

    @Override
    public void deleteResume(Integer resumeId) {
        Optional<Resume> resume = resumeJPARepository.findById(resumeId);
        if (resume.isEmpty()) {
            throw new ResumeNotFoundException(resumeId);
        }

        resumeJPARepository.delete(resume.get());
//        resumeRepository.deleteResume(resumeId);
    }

    @Override
    public void deleteResume(Resume resume) {
        if (!resumeJPARepository.existsById(resume.getId())) {
            throw new ResumeNotFoundException(resume.getId());
        }

        resumeJPARepository.delete(resume);
//        resumeRepository.deleteResume(resume);
    }

    @Override
    public Resume updateResume(Resume resume, Integer resumeId) {
        if (!resumeJPARepository.existsById(resumeId)) {
            throw new ResumeNotFoundException(resumeId);
        }

        Resume unwrappedResume = copyResume(resume);
        return resumeJPARepository.save(unwrappedResume);
    }

    private Resume copyResume(Resume resume) {
        Resume newResume = new Resume();
        newResume.setId(resume.getId());
        newResume.setPersonalInformation(resume.getPersonalInformation());
        newResume.setContactInformation(resume.getContactInformation());
        newResume.setSummary(resume.getSummary());
        newResume.setEducations(resume.getEducations());
        newResume.setTeachingAssistance(resume.getTeachingAssistance());
        newResume.setJobExperiences(resume.getJobExperiences());
        newResume.setFormerColleagues(resume.getFormerColleagues());
        newResume.setResearches(resume.getResearches());
        newResume.setCourses(resume.getCourses());
        newResume.setHardSkills(resume.getHardSkills());
        newResume.setSoftSkills(resume.getSoftSkills());
        newResume.setLanguages(resume.getLanguages());
        newResume.setProjects(resume.getProjects());
        newResume.setPatents(resume.getPatents());
        newResume.setPresentations(resume.getPresentations());
        newResume.setAwards(resume.getAwards());
        newResume.setPresentations(resume.getPresentations());
        newResume.setVolunteerActivities(resume.getVolunteerActivities());
        newResume.setMemberships(resume.getMemberships());
        newResume.setHobbies(resume.getHobbies());
        newResume.setUser(resume.getUser());
        return newResume;
    }

    @Override
    public Resume downloadResume(Integer resumeId, ResumeTheme theme) {
        Optional<Resume> optionalResume = resumeJPARepository.findById(resumeId);
        if (optionalResume.isEmpty()) {
            throw new ResumeNotFoundException(resumeId);
        }

        Resume resume = optionalResume.get();
        createDocumentGenerator(theme).generateWordDocument(resume);
        return resume;
    }

    private DocumentGenerator createDocumentGenerator(ResumeTheme theme) {
        DocumentGenerator documentGenerator1 = null;

        switch (theme) {
            case ATSClassic -> documentGenerator1 = new ATSClassicDocumentGenerator();
            case Bold_Modern -> documentGenerator1 = new BoldModernDocumentGenerator();
            case Classic_Accounting -> documentGenerator1 = new ClassicAccountingDocumentGenerator();
            case Creative_Teaching -> documentGenerator1 = new CreativeTeachingDocumentGenerator();
            case Simple_Florist -> documentGenerator1 = new SimpleFloristDocumentGenerator();
            case Woodworking -> documentGenerator1 = new WoodworkingDocumentGenerator();
        }

        return documentGenerator1;
    }

    @Override
    public List<Resume> findAllResumesByUserEmail(String userEmail) {
        Optional<List<Resume>> allResumes = resumeJPARepository.findAllResumesByUserEmail(userEmail);
        if (allResumes.isEmpty()) {
            throw new UserNotFoundException(userEmail);
        }

        return allResumes.get();
//        return resumeRepository.findAllResumesByUserEmail(userEmail);
    }
}
