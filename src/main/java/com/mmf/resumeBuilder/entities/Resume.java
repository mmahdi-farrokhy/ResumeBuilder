package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ContactMethod> contactInformation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "summary_id", nullable = false)
    private Summary summary;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Education> educations;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<TeachingAssistance> teachingAssistance;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<JobExperience> jobExperiences;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<FormerColleague> formerColleagues;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Research> researches;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Course> courses;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<HardSkill> hardSkills;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<SoftSkill> softSkills;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Language> languages;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Patent> patents;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Presentation> presentations;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Award> awards;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Publication> publications;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<VolunteerActivity> volunteerActivities;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Membership> memberships;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Hobby> hobbies;

    public void addContactMethod(ContactMethod contactMethod) {
        if (contactInformation == null)
            contactInformation = new LinkedList<>();

        contactInformation.add(contactMethod);
    }

    public void removeContactMethod(ContactMethod contactMethod) {
        if (contactInformation != null && contactInformation.contains(contactMethod))
            contactInformation.remove(contactMethod);
    }

    public void addEducation(Education education) {
        if (educations == null)
            educations = new LinkedList<>();

        educations.add(education);
    }

    public void removeEducation(Education education) {
        if (educations != null && educations.contains(education))
            educations.remove(education);
    }

    public void addTeachingAssistance(TeachingAssistance teachingAssistance) {
        if (this.teachingAssistance == null)
            this.teachingAssistance = new LinkedList<>();

        this.teachingAssistance.add(teachingAssistance);
    }

    public void removeTeachingAssistance(TeachingAssistance teachingAssistance) {
        if (this.teachingAssistance != null && educations.contains(teachingAssistance))
            this.teachingAssistance.remove(teachingAssistance);
    }

    public void addJobExperience(JobExperience jobExperience) {
        if (this.jobExperiences == null)
            this.jobExperiences = new LinkedList<>();

        this.jobExperiences.add(jobExperience);
    }

    public void removeTeachingAssistance(JobExperience jobExperience) {
        if (this.jobExperiences != null && this.jobExperiences.contains(jobExperience))
            this.jobExperiences.remove(jobExperience);
    }

    public void addFormerColleague(FormerColleague formerColleague) {
        if (this.formerColleagues == null)
            this.formerColleagues = new LinkedList<>();

        this.formerColleagues.add(formerColleague);
    }

    public void removeFormerColleague(FormerColleague formerColleague) {
        if (this.formerColleagues != null && this.formerColleagues.contains(formerColleague))
            this.formerColleagues.remove(formerColleague);
    }

    public void addResearch(Research research) {
        if (this.researches == null)
            this.researches = new LinkedList<>();

        this.researches.add(research);
    }

    public void removeResearch(Research research) {
        if (this.researches != null && this.researches.contains(research))
            this.researches.remove(research);
    }

    public void addCourse(Course course) {
        if (this.courses == null)
            this.courses = new LinkedList<>();

        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        if (this.courses != null && this.courses.contains(course))
            this.courses.remove(course);
    }

    public void addHardSkill(HardSkill hardSkill) {
        if (this.hardSkills == null)
            this.hardSkills = new LinkedList<>();

        this.hardSkills.add(hardSkill);
    }

    public void removeHardSkill(HardSkill hardSkill) {
        if (this.hardSkills != null && this.hardSkills.contains(hardSkill))
            this.hardSkills.remove(hardSkill);
    }

    public void addSoftSkill(SoftSkill softSkill) {
        if (this.softSkills == null)
            this.softSkills = new LinkedList<>();

        this.softSkills.add(softSkill);
    }

    public void removeSoftSkill(SoftSkill softSkill) {
        if (this.softSkills != null && this.softSkills.contains(softSkill))
            this.softSkills.remove(softSkill);
    }

    public void addLanguage(Language softSkill) {
        if (this.languages == null)
            this.languages = new LinkedList<>();

        this.languages.add(softSkill);
    }

    public void removeLanguage(Language language) {
        if (this.languages != null && this.languages.contains(language))
            this.languages.remove(language);
    }

    public void addProject(Project project) {
        if (this.projects == null)
            this.projects = new LinkedList<>();

        this.projects.add(project);
    }

    public void removeProject(Project project) {
        if (this.projects != null && this.projects.contains(project))
            this.projects.remove(project);
    }

    public void addPatent(Patent patent) {
        if (this.patents == null)
            this.patents = new LinkedList<>();

        this.patents.add(patent);
    }

    public void removePatent(Project project) {
        if (this.patents != null && this.patents.contains(project))
            this.patents.remove(project);
    }

    public void addPresentation(Presentation presentation) {
        if (this.presentations == null)
            this.presentations = new LinkedList<>();

        this.presentations.add(presentation);
    }

    public void removePresentation(Presentation presentation) {
        if (this.presentations != null && this.presentations.contains(presentation))
            this.presentations.remove(presentation);
    }

    public void addAward(Award award) {
        if (this.awards == null)
            this.awards = new LinkedList<>();

        this.awards.add(award);
    }

    public void removeAward(Award award) {
        if (this.awards != null && this.awards.contains(award))
            this.awards.remove(award);
    }

    public void addPublication(Publication publication) {
        if (this.publications == null)
            this.publications = new LinkedList<>();

        this.publications.add(publication);
    }

    public void removePublication(Publication publication) {
        if (this.publications != null && this.publications.contains(publication))
            this.publications.remove(publication);
    }

    public void addVolunteerActivity(VolunteerActivity volunteerActivity) {
        if (this.volunteerActivities == null)
            this.volunteerActivities = new LinkedList<>();

        this.volunteerActivities.add(volunteerActivity);
    }

    public void removeVolunteerActivity(VolunteerActivity volunteerActivity) {
        if (this.volunteerActivities != null && this.volunteerActivities.contains(volunteerActivity))
            this.volunteerActivities.remove(volunteerActivity);
    }

    public void addMembership(Membership membership) {
        if (this.memberships == null)
            this.memberships = new LinkedList<>();

        this.memberships.add(membership);
    }

    public void removeMembership(Membership membership) {
        if (this.memberships != null && this.memberships.contains(membership))
            this.memberships.remove(membership);
    }

    public void addHobby(Hobby hobby) {
        if (this.hobbies == null)
            this.hobbies = new LinkedList<>();

        this.hobbies.add(hobby);
    }

    public void removeHobby(Hobby hobby) {
        if (this.hobbies != null && this.hobbies.contains(hobby))
            this.hobbies.remove(hobby);
    }
}
