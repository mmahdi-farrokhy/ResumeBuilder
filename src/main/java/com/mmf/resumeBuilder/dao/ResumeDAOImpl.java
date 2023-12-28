package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResumeDAOImpl implements ResumeDAO {
    public static final String CONTACT_INFORMATION_QUERY = "select r from Resume r LEFT JOIN FETCH r.contactInformation where r.id = :resumeId";
    public static final String EDUCATIONS_QUERY = "select r from Resume r LEFT JOIN FETCH r.educations where r.id = :resumeId";
    public static final String TEACHING_ASSISTANCE_QUERY = "select r from Resume r LEFT JOIN FETCH r.teachingAssistance where r.id = :resumeId";
    public static final String JOB_EXPERIENCES_QUERY = "select r from Resume r LEFT JOIN FETCH r.jobExperiences where r.id = :resumeId";
    public static final String FORMER_COLLEAGUES_QUERY = "select r from Resume r LEFT JOIN FETCH r.formerColleagues where r.id = :resumeId";
    public static final String RESEARCHES_QUERY = "select r from Resume r LEFT JOIN FETCH r.researches where r.id = :resumeId";
    public static final String COURSES_QUERY = "select r from Resume r LEFT JOIN FETCH r.courses where r.id = :resumeId";
    public static final String HARD_SKILLS_QUERY = "select r from Resume r LEFT JOIN FETCH r.hardSkills where r.id = :resumeId";
    public static final String SOFT_SKILLS_QUERY = "select r from Resume r LEFT JOIN FETCH r.softSkills where r.id = :resumeId";
    public static final String LANGUAGES_QUERY = "select r from Resume r LEFT JOIN FETCH r.languages where r.id = :resumeId";
    public static final String PROJECTS_QUERY = "select r from Resume r LEFT JOIN FETCH r.projects where r.id = :resumeId";
    public static final String PATENTS_QUERY = "select r from Resume r LEFT JOIN FETCH r.patents where r.id = :resumeId";
    public static final String PRESENTATIONS_QUERY = "select r from Resume r LEFT JOIN FETCH r.presentations where r.id = :resumeId";
    public static final String AWARDS_QUERY = "select r from Resume r LEFT JOIN FETCH r.awards where r.id = :resumeId";
    public static final String PUBLICATIONS_QUERY = "select r from Resume r LEFT JOIN FETCH r.publications where r.id = :resumeId";
    public static final String VOLUNTEER_ACTIVITIES_QUERY = "select r from Resume r LEFT JOIN FETCH r.volunteerActivities where r.id = :resumeId";
    public static final String MEMBERSHIPS_QUERY = "select r from Resume r LEFT JOIN FETCH r.memberships where r.id = :resumeId";
    public static final String HOBBIES_QUERY = "select r from Resume r LEFT JOIN FETCH r.hobbies where r.id = :resumeId";
    private final EntityManager entityManager;

    @Autowired
    public ResumeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Resume findById(int resumeId) {
        return entityManager.find(Resume.class, resumeId);
    }

    @Override
    @Transactional
    public void save(Resume resume) {
        entityManager.persist(resume);
    }

    @Override
    public <RS extends ResumeSection> List<RS> fetchSection(Integer resumeId, Class<RS> sectionType) {
        TypedQuery<Resume> query = entityManager.createQuery(createQueryString(sectionType), Resume.class);
        query.setParameter("resumeId", resumeId);
        return getResumeSections(query.getSingleResult(), sectionType);
    }

    private static <RS extends ResumeSection> String createQueryString(Class<RS> sectionType) {
        String query = "";

        switch (sectionType.getSimpleName()) {
            case "ContactMethod" -> query = CONTACT_INFORMATION_QUERY;
            case "Education" -> query = EDUCATIONS_QUERY;
            case "TeachingAssistance" -> query = TEACHING_ASSISTANCE_QUERY;
            case "JobExperience" -> query = JOB_EXPERIENCES_QUERY;
            case "FormerColleague" -> query = FORMER_COLLEAGUES_QUERY;
            case "Research" -> query = RESEARCHES_QUERY;
            case "Course" -> query = COURSES_QUERY;
            case "HardSkill" -> query = HARD_SKILLS_QUERY;
            case "SoftSkill" -> query = SOFT_SKILLS_QUERY;
            case "Language" -> query = LANGUAGES_QUERY;
            case "Project" -> query = PROJECTS_QUERY;
            case "Patent" -> query = PATENTS_QUERY;
            case "Presentation" -> query = PRESENTATIONS_QUERY;
            case "Award" -> query = AWARDS_QUERY;
            case "Publication" -> query = PUBLICATIONS_QUERY;
            case "VolunteerActivity" -> query = VOLUNTEER_ACTIVITIES_QUERY;
            case "Membership" -> query = MEMBERSHIPS_QUERY;
            case "Hobby" -> query = HOBBIES_QUERY;
        }

        return query;
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
    public void updateSection(ResumeSection updatingSection) {
        entityManager.merge(updatingSection);
    }

    @Override
    @Transactional
    public void deleteSection(ResumeSection deletingSection) {
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
}
