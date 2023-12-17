package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private List<VolunteerActivity> volunteerActivities;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Membership> memberships;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Hobby> hobbies;
}
