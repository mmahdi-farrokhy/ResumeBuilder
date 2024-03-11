package com.mmf.resumeBuilder.repository;

import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.entity.resume.ResumeSection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ResumeRepositoryImpl implements ResumeRepository {

    private EntityManager entityManager;

    @Override
    public List<Resume> findAllResumes() {
        String sql = "select * from Resume";
        Query query = entityManager.createNativeQuery(sql, Resume.class);
        return query.getResultList();
    }

    @Override
    public Resume findResumeById(int resumeId) {
        return entityManager.find(Resume.class, resumeId);
    }

    @Override
    @Transactional
    public Resume saveResume(Resume resume) {
        entityManager.persist(resume);
        return resume;
    }

    @Override
    public void deleteResume(Integer resumeId) {
        entityManager.remove(resumeId);
    }

    @Override
    public void deleteResume(Resume resume) {
        entityManager.remove(resume);
    }

    @Override
    public <RS extends ResumeSection> List<RS> findResumeSection(Integer resumeId, Class<RS> sectionType) {
        TypedQuery<Resume> query = entityManager.createQuery(queryString(sectionType), Resume.class);
        query.setParameter("resumeId", resumeId);
        return getResumeSections(query.getSingleResult(), sectionType);
    }

    private static <RS extends ResumeSection> String queryString(Class<RS> sectionType) {
        final String ROOT_QUERY = "select r from Resume r LEFT JOIN FETCH r.resumeSection where r.id = :resumeId";
        String resumeSection = "";
        switch (sectionType.getSimpleName()) {
            case "ContactMethod" -> resumeSection = "contactInformation";
            case "Education" -> resumeSection = "educations";
            case "TeachingAssistance" -> resumeSection = "teachingAssistance";
            case "JobExperience" -> resumeSection = "jobExperiences";
            case "FormerColleague" -> resumeSection = "formerColleagues";
            case "Research" -> resumeSection = "researches";
            case "Course" -> resumeSection = "courses";
            case "HardSkill" -> resumeSection = "hardSkills";
            case "SoftSkill" -> resumeSection = "softSkills";
            case "Language" -> resumeSection = "languages";
            case "Project" -> resumeSection = "projects";
            case "Patent" -> resumeSection = "patents";
            case "Presentation" -> resumeSection = "presentations";
            case "Award" -> resumeSection = "awards";
            case "Publication" -> resumeSection = "publications";
            case "VolunteerActivity" -> resumeSection = "volunteerActivities";
            case "Membership" -> resumeSection = "memberships";
            case "Hobby" -> resumeSection = "hobbies";
        }

        return ROOT_QUERY.replace("resumeSection", resumeSection);
    }

    private static <RS extends ResumeSection> List<RS> getResumeSections(Resume resume, Class<RS> sectionType) {
        List<? extends ResumeSection> items;

        switch (sectionType.getSimpleName()) {
            case "ContactMethod" -> items = resume.getContactInformation();
            case "Education" -> items = resume.getEducations();
            case "TeachingAssistance" -> items = resume.getTeachingAssistance();
            case "JobExperience" -> items = resume.getJobExperiences();
            case "FormerColleague" -> items = resume.getFormerColleagues();
            case "Research" -> items = resume.getResearches();
            case "Course" -> items = resume.getCourses();
            case "HardSkill" -> items = resume.getHardSkills();
            case "SoftSkill" -> items = resume.getSoftSkills();
            case "Language" -> items = resume.getLanguages();
            case "Project" -> items = resume.getProjects();
            case "Patent" -> items = resume.getPatents();
            case "Presentation" -> items = resume.getPresentations();
            case "Award" -> items = resume.getAwards();
            case "Publication" -> items = resume.getPublications();
            case "VolunteerActivity" -> items = resume.getVolunteerActivities();
            case "Membership" -> items = resume.getMemberships();
            case "Hobby" -> items = resume.getHobbies();
            default -> items = new ArrayList<>();
        }

        return (List<RS>) items;
    }

    @Override
    @Transactional
    public void updateResumeSection(ResumeSection updatingSection) {
        entityManager.merge(updatingSection);
    }

    @Override
    @Transactional
    public void deleteResumeSection(ResumeSection deletingSection) {
        try {
            Class<? extends ResumeSection> sectionType = deletingSection.getClass();
            int sectionId = getSectionId(deletingSection, sectionType);
            ResumeSection deletingSectionInDb = entityManager.find(sectionType, sectionId);
            Resume resume = getResume(sectionType, deletingSectionInDb);
            resume.removeSection(deletingSectionInDb);
            entityManager.remove(deletingSectionInDb);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static int getSectionId(ResumeSection deletingSection, Class<? extends ResumeSection> sectionType) throws NoSuchFieldException, IllegalAccessException {
        Field idField = sectionType.getDeclaredField("id");
        idField.setAccessible(true);
        int sectionId = (int) idField.get(deletingSection);
        return sectionId;
    }

    private static Resume getResume(Class<? extends ResumeSection> sectionType, ResumeSection deletingSectionInDb) throws NoSuchFieldException, IllegalAccessException {
        Field resumeField = sectionType.getDeclaredField("resume");
        resumeField.setAccessible(true);
        Resume resume = (Resume) resumeField.get(deletingSectionInDb);
        return resume;
    }

    @Override
    @Transactional
    public <RS extends ResumeSection> void addResumeSection(RS resumeSection) {
        entityManager.persist(resumeSection);
    }

    @Override
    public List<Resume> findAllResumesByUserEmail(String userEmail) {
        Query query = entityManager.createQuery("SELECT r FROM Resume r WHERE r.appUser.email = :userEmail");
        query.setParameter("userEmail", userEmail);
        return (List<Resume>) query.getResultList();
    }
}
