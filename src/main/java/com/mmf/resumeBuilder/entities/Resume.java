package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id + "\n" +
                '}';
    }

    public <T extends ResumeSection> void addSection(T section) {
        if (section instanceof ContactMethod) {
            if (contactInformation == null)
                contactInformation = new LinkedList<>();

            contactInformation.add((ContactMethod) section);
        } else if (section instanceof Education) {
            if (educations == null)
                educations = new LinkedList<>();

            educations.add((Education) section);
        } else if (section instanceof TeachingAssistance) {
            if (teachingAssistance == null)
                teachingAssistance = new LinkedList<>();

            teachingAssistance.add((TeachingAssistance) section);
        } else if (section instanceof JobExperience) {
            if (jobExperiences == null)
                jobExperiences = new LinkedList<>();

            jobExperiences.add((JobExperience) section);
        } else if (section instanceof FormerColleague) {
            if (formerColleagues == null)
                formerColleagues = new LinkedList<>();

            formerColleagues.add((FormerColleague) section);
        } else if (section instanceof Research) {
            if (researches == null)
                researches = new LinkedList<>();

            researches.add((Research) section);
        } else if (section instanceof Course) {
            if (courses == null)
                courses = new LinkedList<>();

            courses.add((Course) section);
        } else if (section instanceof HardSkill) {
            if (hardSkills == null)
                hardSkills = new LinkedList<>();

            hardSkills.add((HardSkill) section);
        } else if (section instanceof SoftSkill) {
            if (softSkills == null)
                softSkills = new LinkedList<>();

            softSkills.add((SoftSkill) section);
        } else if (section instanceof Language) {
            if (languages == null)
                languages = new LinkedList<>();

            languages.add((Language) section);
        } else if (section instanceof Project) {
            if (projects == null)
                projects = new LinkedList<>();

            projects.add((Project) section);
        } else if (section instanceof Patent) {
            if (patents == null)
                patents = new LinkedList<>();

            patents.add((Patent) section);
        } else if (section instanceof Presentation) {
            if (presentations == null)
                presentations = new LinkedList<>();

            presentations.add((Presentation) section);
        } else if (section instanceof Publication) {
            if (publications == null)
                publications = new LinkedList<>();

            publications.add((Publication) section);
        } else if (section instanceof VolunteerActivity) {
            if (volunteerActivities == null)
                volunteerActivities = new LinkedList<>();

            volunteerActivities.add((VolunteerActivity) section);
        } else if (section instanceof Membership) {
            if (memberships == null)
                memberships = new LinkedList<>();

            memberships.add((Membership) section);
        } else if (section instanceof Hobby) {
            if (hobbies == null)
                hobbies = new LinkedList<>();

            hobbies.add((Hobby) section);
        }
    }

    public <T extends ResumeSection> void removeSection(T section) {
        if (section instanceof ContactMethod) {
            if (this.contactInformation != null && this.contactInformation.contains(section))
                contactInformation.remove(section);
        } else if (section instanceof Education) {
            if (this.educations != null && this.educations.contains(section))
                educations.remove(section);
        } else if (section instanceof TeachingAssistance) {
            if (this.teachingAssistance != null && this.teachingAssistance.contains(section))
                teachingAssistance.remove(section);
        } else if (section instanceof JobExperience) {
            if (this.jobExperiences != null && this.jobExperiences.contains(section))
                jobExperiences.remove(section);
        } else if (section instanceof FormerColleague) {
            if (this.formerColleagues != null && this.formerColleagues.contains(section))
                formerColleagues.remove(section);
        } else if (section instanceof Research) {
            if (this.researches != null && this.researches.contains(section))
                researches.remove(section);
        } else if (section instanceof Course) {
            if (this.courses != null && this.courses.contains(section))
                courses.remove(section);
        } else if (section instanceof HardSkill) {
            if (this.hardSkills != null && this.hardSkills.contains(section))
                hardSkills.remove(section);
        } else if (section instanceof SoftSkill) {
            if (this.softSkills != null && this.softSkills.contains(section))
                softSkills.remove(section);
        } else if (section instanceof Language) {
            if (this.languages != null && this.languages.contains(section))
                languages.remove(section);
        } else if (section instanceof Project) {
            if (this.projects != null && this.projects.contains(section))
                projects.remove(section);
        } else if (section instanceof Patent) {
            if (this.patents != null && this.patents.contains(section))
                patents.remove(section);
        } else if (section instanceof Presentation) {
            if (this.presentations != null && this.presentations.contains(section))
                presentations.remove(section);
        } else if (section instanceof Publication) {
            if (this.publications != null && this.publications.contains(section))
                publications.remove(section);
        } else if (section instanceof VolunteerActivity) {
            if (this.volunteerActivities != null && this.volunteerActivities.contains(section))
                volunteerActivities.remove(section);
        } else if (section instanceof Membership) {
            if (this.memberships != null && this.memberships.contains(section))
                memberships.remove(section);
        } else if (section instanceof Hobby) {
            if (this.hobbies != null && this.hobbies.contains(section))
                hobbies.remove(section);
        }
    }
}
