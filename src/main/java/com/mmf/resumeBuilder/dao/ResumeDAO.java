package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.*;

import java.util.List;

public interface ResumeDAO {
    Resume findById(int resumeId);

    void save(Resume resume);

    User fetchUser(Integer resumeId);

    <T extends ResumeSection> List<ContactMethod> fetchContactInformation(Integer resumeId, Class<T> sectionType);

    List<ContactMethod> fetchContactInformation(Integer resumeId);


    List<Education> fetchEducations(Integer resumeId);

    List<TeachingAssistance> fetchTeachingAssistance(Integer resumeId);

    List<JobExperience> fetchJobExperiences(Integer resumeId);

    List<FormerColleague> fetchFormerColleagues(Integer resumeId);

    List<Research> fetchResearches(Integer resumeId);

    List<Course> fetchCourses(Integer resumeId);

    List<HardSkill> fetchHardSkills(Integer resumeId);

    List<SoftSkill> fetchSoftSkills(Integer resumeId);

    List<Language> fetchLanguages(Integer resumeId);

    List<Project> fetchProjects(Integer resumeId);

    List<Patent> fetchPatents(Integer resumeId);

    List<Presentation> fetchPresentations(Integer resumeId);

    List<Award> fetchAwards(Integer resumeId);

    List<Publication> fetchPublications(Integer resumeId);

    List<VolunteerActivity> fetchVolunteerActivities(Integer resumeId);

    List<Membership> fetchMemberships(Integer resumeId);

    List<Hobby> fetchHobbies(Integer resumeId);

    void updateSection(ResumeSection updatingSection);

    void deleteSection(ResumeSection deletingSection);
}
