package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ResumeDAOImpl implements ResumeDAO {
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
    public void save(Resume resume) {
        entityManager.persist(resume);
    }

    @Override
    public User findUser(Integer resumeId) {
        return findById(resumeId).getUser();
    }

    @Override
    public List<ContactMethod> findContactInformation(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.contactInformation " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getContactInformation();
    }

    @Override
    @Transactional
    public void deleteContactMethod(Integer deletingContactMethodId) {
        ContactMethod deletingContactMethod = entityManager.find(ContactMethod.class, deletingContactMethodId);
        Resume resume = deletingContactMethod.getResume();
        resume.removeContactMethod(deletingContactMethod);
        entityManager.remove(deletingContactMethod);
    }

    @Override
    public List<Education> findEducations(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.educations " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getEducations();
    }

    @Override
    @Transactional
    public void deleteEducation(Integer deletingEducationId) {
        Education deletingEducation = entityManager.find(Education.class, deletingEducationId);
        Resume resume = deletingEducation.getResume();
        resume.removeEducation(deletingEducation);
        entityManager.remove(deletingEducation);
    }

    @Override
    public List<TeachingAssistance> findTeachingAssistance(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.teachingAssistance " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getTeachingAssistance();
    }

    @Override
    @Transactional
    public void deleteTeachingAssistance(Integer deletingTeachingAssistanceId) {
        TeachingAssistance deletingTeachingAssistance = entityManager.find(TeachingAssistance.class, deletingTeachingAssistanceId);
        Resume resume = deletingTeachingAssistance.getResume();
        resume.removeTeachingAssistance(deletingTeachingAssistance);
        entityManager.remove(deletingTeachingAssistance);
    }

    @Override
    public List<JobExperience> findJobExperiences(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.jobExperiences " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getJobExperiences();
    }

    @Override
    public List<FormerColleague> findFormerColleagues(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.formerColleagues " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getFormerColleagues();
    }

    @Override
    public List<Research> findResearches(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.researches " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getResearches();
    }

    @Override
    public List<Course> findCourses(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.courses " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getCourses();
    }

    @Override
    public List<HardSkill> findHardSkills(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.hardSkills " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getHardSkills();
    }

    @Override
    public List<SoftSkill> findSoftSkills(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.softSkills " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getSoftSkills();
    }

    @Override
    public List<Language> findLanguages(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.languages " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getLanguages();
    }

    @Override
    public List<Project> findProjects(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.projects " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getProjects();
    }

    @Override
    public List<Patent> findPatents(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.patents " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getPatents();
    }

    @Override
    public List<Presentation> findPresentations(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.presentations " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getPresentations();
    }

    @Override
    public List<Award> findAwards(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.awards " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getAwards();
    }

    @Override
    public List<Publication> findPublications(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.publications " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getPublications();
    }

    @Override
    public List<VolunteerActivity> findVolunteerActivities(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.volunteerActivities " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getVolunteerActivities();
    }

    @Override
    public List<Membership> findMemberships(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.memberships " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getMemberships();
    }

    @Override
    public List<Hobby> findHobbies(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.hobbies " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getHobbies();
    }

    @Override
    @Transactional
    public <T extends ResumeSection> void updateSection(T updatingSection) {
        entityManager.merge(updatingSection);
    }

    @Override
    @Transactional
    public void deleteCourse(Integer deletingCourseId) {
        Course deletingCourse = entityManager.find(Course.class, deletingCourseId);
        Resume resume = deletingCourse.getResume();
        resume.removeCourse(deletingCourse);
        entityManager.remove(deletingCourse);
    }
}
