package com.mmf.resumeBuilder.entity.resume;

import com.mmf.resumeBuilder.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "resume")
public class Resume implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "personal_information_id", nullable = false)
    private PersonalInformation personalInformation;

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
    private List<Membership> memberships;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Hobby> hobbies;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<VolunteerActivity> volunteerActivities;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "app_user_id")
    @NotNull
    private User user;

    public <T extends ResumeSection> void addSection(T section) {
        if (section instanceof ContactMethod) {
            if (contactInformation == null)
                contactInformation = new LinkedList<>();

            contactInformation.add((ContactMethod) section);
            ((ContactMethod) section).setResume(this);
        } else if (section instanceof Education) {
            if (educations == null)
                educations = new LinkedList<>();

            educations.add((Education) section);
            ((Education) section).setResume(this);
        } else if (section instanceof TeachingAssistance) {
            if (teachingAssistance == null)
                teachingAssistance = new LinkedList<>();

            teachingAssistance.add((TeachingAssistance) section);
            ((TeachingAssistance) section).setResume(this);
        } else if (section instanceof JobExperience) {
            if (jobExperiences == null)
                jobExperiences = new LinkedList<>();

            jobExperiences.add((JobExperience) section);
            ((JobExperience) section).setResume(this);
        } else if (section instanceof FormerColleague) {
            if (formerColleagues == null)
                formerColleagues = new LinkedList<>();

            formerColleagues.add((FormerColleague) section);
            ((FormerColleague) section).setResume(this);
        } else if (section instanceof Research) {
            if (researches == null)
                researches = new LinkedList<>();

            researches.add((Research) section);
            ((Research) section).setResume(this);
        } else if (section instanceof Course) {
            if (courses == null)
                courses = new LinkedList<>();

            courses.add((Course) section);
            ((Course) section).setResume(this);
        } else if (section instanceof HardSkill) {
            if (hardSkills == null)
                hardSkills = new LinkedList<>();

            hardSkills.add((HardSkill) section);
            ((HardSkill) section).setResume(this);
        } else if (section instanceof SoftSkill) {
            if (softSkills == null)
                softSkills = new LinkedList<>();

            softSkills.add((SoftSkill) section);
            ((SoftSkill) section).setResume(this);
        } else if (section instanceof Language) {
            if (languages == null)
                languages = new LinkedList<>();

            languages.add((Language) section);
            ((Language) section).setResume(this);
        } else if (section instanceof Project) {
            if (projects == null)
                projects = new LinkedList<>();

            projects.add((Project) section);
            ((Project) section).setResume(this);
        } else if (section instanceof Patent) {
            if (patents == null)
                patents = new LinkedList<>();

            patents.add((Patent) section);
            ((Patent) section).setResume(this);
        } else if (section instanceof Presentation) {
            if (presentations == null)
                presentations = new LinkedList<>();

            presentations.add((Presentation) section);
            ((Presentation) section).setResume(this);
        } else if (section instanceof Publication) {
            if (publications == null)
                publications = new LinkedList<>();

            publications.add((Publication) section);
            ((Publication) section).setResume(this);
        } else if (section instanceof VolunteerActivity) {
            if (volunteerActivities == null)
                volunteerActivities = new LinkedList<>();

            volunteerActivities.add((VolunteerActivity) section);
            ((VolunteerActivity) section).setResume(this);
        } else if (section instanceof Membership) {
            if (memberships == null)
                memberships = new LinkedList<>();

            memberships.add((Membership) section);
            ((Membership) section).setResume(this);
        } else if (section instanceof Hobby) {
            if (hobbies == null)
                hobbies = new LinkedList<>();

            hobbies.add((Hobby) section);
            ((Hobby) section).setResume(this);
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

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id + "\n" +
                '}';
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(personalInformation, resume.personalInformation) && Objects.equals(contactInformation, resume.contactInformation) && Objects.equals(summary, resume.summary) && Objects.equals(educations, resume.educations) && Objects.equals(teachingAssistance, resume.teachingAssistance) && Objects.equals(jobExperiences, resume.jobExperiences) && Objects.equals(formerColleagues, resume.formerColleagues) && Objects.equals(researches, resume.researches) && Objects.equals(courses, resume.courses) && Objects.equals(hardSkills, resume.hardSkills) && Objects.equals(softSkills, resume.softSkills) && Objects.equals(languages, resume.languages) && Objects.equals(projects, resume.projects) && Objects.equals(patents, resume.patents) && Objects.equals(presentations, resume.presentations) && Objects.equals(awards, resume.awards) && Objects.equals(publications, resume.publications) && Objects.equals(volunteerActivities, resume.volunteerActivities) && Objects.equals(memberships, resume.memberships) && Objects.equals(hobbies, resume.hobbies) && Objects.equals(user, resume.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalInformation, contactInformation, summary, educations, teachingAssistance, jobExperiences, formerColleagues, researches, courses, hardSkills, softSkills, languages, projects, patents, presentations, awards, publications, volunteerActivities, memberships, hobbies, user);
    }
}
