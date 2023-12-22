package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.*;

import java.util.List;

public interface ResumeDAO {
    Resume findById(int resumeId);
    void save(Resume resume);
    User findUser(Integer resumeId);

    List<ContactMethod> findContactInformation(Integer resumeId);

    List<Education> findEducations(Integer resumeId);

    List<TeachingAssistance> findTeachingAssistance(Integer resumeId);

    List<JobExperience> findJobExperiences(Integer resumeId);

    List<FormerColleague> findFormerColleagues(Integer resumeId);

    List<Research> findResearches(Integer resumeId);

    List<Course> findCourses(Integer resumeId);

    List<HardSkill> findHardSkills(Integer resumeId);

    List<SoftSkill> findSoftSkills(Integer resumeId);

    List<Language> findLanguages(Integer resumeId);

    List<Project> findProjects(Integer resumeId);

    List<Patent> findPatents(Integer resumeId);

    List<Presentation> findPresentations(Integer resumeId);

    List<Award> findAwards(Integer resumeId);

    List<Publication> findPublications(Integer resumeId);

    List<VolunteerActivity> findVolunteerActivities(Integer resumeId);

    List<Membership> findMemberships(Integer resumeId);

    List<Hobby> findHobbies(Integer resumeId);
}
