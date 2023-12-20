package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.ContactMethod;
import com.mmf.resumeBuilder.entities.Education;
import com.mmf.resumeBuilder.entities.Resume;
import com.mmf.resumeBuilder.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public Resume eagerFindById(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.contactInformation " +
                "LEFT JOIN FETCH r.educations " +
                "LEFT JOIN FETCH r.teachingAssistance " +
                "LEFT JOIN FETCH r.jobExperiences " +
                "LEFT JOIN FETCH r.formerColleagues " +
                "LEFT JOIN FETCH r.researches " +
                "LEFT JOIN FETCH r.courses " +
                "LEFT JOIN FETCH r.hardSkills " +
                "LEFT JOIN FETCH r.softSkills " +
                "LEFT JOIN FETCH r.languages " +
                "LEFT JOIN FETCH r.projects " +
                "LEFT JOIN FETCH r.patents " +
                "LEFT JOIN FETCH r.presentations " +
                "LEFT JOIN FETCH r.awards " +
                "LEFT JOIN FETCH r.publications " +
                "LEFT JOIN FETCH r.volunteerActivities " +
                "LEFT JOIN FETCH r.memberships " +
                "LEFT JOIN FETCH r.hobbies " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult();
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
    public List<Education> findEducations(Integer resumeId) {
        TypedQuery<Resume> query = entityManager.createQuery("select r from Resume r " +
                "LEFT JOIN FETCH r.educations " +
                "where r.id = :resumeId", Resume.class);

        query.setParameter("resumeId", resumeId);
        return query.getSingleResult().getEducations();
    }
}
