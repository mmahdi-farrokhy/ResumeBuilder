package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.entity.resume.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;

public class DocumentGenerator {

    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    public static final int INDENTATION = 300;

    public static void createNewWordDocument(Resume resume) {
        try {
            XWPFDocument document = new XWPFDocument();

            PersonalInformation personalInformation = resume.getPersonalInformation();
            List<ContactMethod> contactInformation = resume.getContactInformation();
            Summary summary = resume.getSummary();
            List<JobExperience> jobExperiences = resume.getJobExperiences();
            List<FormerColleague> formerColleagues = resume.getFormerColleagues();
            List<HardSkill> hardSkills = resume.getHardSkills();
            List<SoftSkill> softSkills = resume.getSoftSkills();
            List<Course> courses = resume.getCourses();
            List<Project> projects = resume.getProjects();
            List<Education> educations = resume.getEducations();
            List<TeachingAssistance> teachingAssistance = resume.getTeachingAssistance();
            List<Presentation> presentations = resume.getPresentations();
            List<Patent> patents = resume.getPatents();
            List<Research> researches = resume.getResearches();
            List<Language> languages = resume.getLanguages();
            List<Hobby> hobbies = resume.getHobbies();
            List<Membership> memberships = resume.getMemberships();
            List<VolunteerActivity> volunteerActivities = resume.getVolunteerActivities();

            if (personalInformation != null) {
                createNameTitle(document, personalInformation);
            }

            if (contactInformation != null && !contactInformation.isEmpty()) {
                addContactInformation(document, contactInformation);
            }

            if (summary != null) {
                addSummary(document, summary);
            }

            if (jobExperiences != null && !jobExperiences.isEmpty()) {
                addJobExperiencesToDocument(document, jobExperiences);
            }

            if (formerColleagues != null && !formerColleagues.isEmpty()) {
                addFormerColleaguesToDocument(document, formerColleagues);
            }

            if ((hardSkills != null && !hardSkills.isEmpty()) || (softSkills != null && !softSkills.isEmpty())) {
                addSkillsToDocument(document, hardSkills, softSkills);
            }

            if (courses != null && !courses.isEmpty()) {
                addCoursesToDocument(document, courses);
            }

            if (projects != null && !projects.isEmpty()) {
                addProjectsToDocument(document, projects);
            }

            if (educations != null && !educations.isEmpty()) {
                addEducationsToDocument(document, educations);
            }

            if (teachingAssistance != null && !teachingAssistance.isEmpty()) {
                addTeachingAssistanceToDocument(document, teachingAssistance);
            }

            if (presentations != null && !presentations.isEmpty()) {
                addPresentationsToDocument(document, presentations);
            }

            if (patents != null && !patents.isEmpty()) {
                addPatentsToDocument(document, patents);
            }

            if (researches != null && !researches.isEmpty()) {
                addResearchesToDocument(document, researches);
            }

            if (languages != null && !languages.isEmpty()) {
                addLanguagesToDocument(document, languages);
            }

            if (hobbies != null && !hobbies.isEmpty()) {
                addHobbiesToDocument(document, hobbies);
            }

            if (memberships != null && !memberships.isEmpty()) {
                addMembershipsToDocument(document, memberships);
            }

            if (volunteerActivities != null && !volunteerActivities.isEmpty()) {
                addVolunteerActivitiesToDocument(document, volunteerActivities);
            }

            FileOutputStream out = new FileOutputStream(STORE_PATH + "NameTest.docx");
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addVolunteerActivitiesToDocument(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Volunteer Activities", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (VolunteerActivity volunteerActivity : volunteerActivities) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            createBodyRun(bodyParagraph, volunteerActivity.getTitle() + " | " + volunteerActivity.getYear(), false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addMembershipsToDocument(XWPFDocument document, List<Membership> memberships) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Memberships", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (Membership membership : memberships) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            String presentationTitle = membership.getTitle() + " | " + membership.getDate();
            createBodyRun(bodyParagraph, presentationTitle, false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addHobbiesToDocument(XWPFDocument document, List<Hobby> hobbies) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Hobbies", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (Hobby hobby : hobbies) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            createBodyRun(bodyParagraph, hobby.getTitle(), false);
            addBulletToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
        }

        insertNewLine(bodyParagraph);
    }

    private static void addLanguagesToDocument(XWPFDocument document, List<Language> languages) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Languages", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (Language language : languages) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            String presentationTitle = language.getName() + ": " + language.estimateAverageLevel();
            createBodyRun(bodyParagraph, presentationTitle, false);
            addBulletToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
        }

        insertNewLine(bodyParagraph);
    }

    private static void addResearchesToDocument(XWPFDocument document, List<Research> researches) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Researches", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        int researchNumber = 0;
        for (Research research : researches) {
            researchNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            String presentationTitle = research.getTitle() + " | " + research.getPublisher() + " | " + research.getDate();
            createBodyRun(bodyParagraph, presentationTitle, true);
            insertNewLine(bodyParagraph);

            if (research.getReferenceLink() != null) {
                createHyperlinkRun(bodyParagraph, research.getReferenceLink(), "Research link");
                insertNewLine(bodyParagraph);
            }

            if (research.getDescription() != null) {
                createBodyRun(bodyParagraph, research.getDescription(), false);
                insertNewLine(bodyParagraph);
            }

            if (researchNumber < researches.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addPatentsToDocument(XWPFDocument document, List<Patent> patents) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Patents", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        int patentNumber = 0;
        for (Patent patent : patents) {
            patentNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            String presentationTitle = patent.getTitle() + " | " + patent.getRegistrationNumber() + " | " + patent.getRegistrationDate();
            createBodyRun(bodyParagraph, presentationTitle, true);
            insertNewLine(bodyParagraph);

            if (patent.getReferenceLink() != null) {
                createHyperlinkRun(bodyParagraph, patent.getReferenceLink(), "More about the patent");
                insertNewLine(bodyParagraph);
            }

            if (patent.getDescription() != null) {
                createBodyRun(bodyParagraph, patent.getDescription(), false);
                insertNewLine(bodyParagraph);
            }

            if (patentNumber < patents.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addPresentationsToDocument(XWPFDocument document, List<Presentation> presentations) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Presentations", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        int presentationNumber = 0;
        for (Presentation presentation : presentations) {
            presentationNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            String presentationTitle = presentation.getTitle() + " | " + presentation.getDate();
            createBodyRun(bodyParagraph, presentationTitle, true);
            insertNewLine(bodyParagraph);

            if (presentation.getReferenceLink() != null) {
                createHyperlinkRun(bodyParagraph, presentation.getReferenceLink(), "More about the presentation");
                insertNewLine(bodyParagraph);
            }

            if (presentation.getDescription() != null) {
                createBodyRun(bodyParagraph, presentation.getDescription(), false);
                insertNewLine(bodyParagraph);
            }

            if (presentationNumber < presentations.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addTeachingAssistanceToDocument(XWPFDocument document, List<TeachingAssistance> teachingAssistance) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Teaching Assistance", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (TeachingAssistance ta : teachingAssistance) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            String teachingAssistanceTitle = ta.getTitle() + " | " + ta.getUniversity() + " | " + calculateDuration(ta.getStartDate(), ta.getEndDate());
            createBodyRun(bodyParagraph, teachingAssistanceTitle, false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addEducationsToDocument(XWPFDocument document, List<Education> educations) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Education", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
        int educationNumber = 0;
        for (Education education : educations) {
            educationNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            createBodyRun(bodyParagraph, calculateEducationDuration(education), false);
            insertNewLine(bodyParagraph);
            createBodyRun(bodyParagraph, extractEducationTitle(education), true);
            insertNewLine(bodyParagraph);

            if (educationNumber < educations.size())
                insertNewLine(bodyParagraph);
        }

    }

    private static void addProjectsToDocument(XWPFDocument document, List<Project> projects) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Projects", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        int projectNumber = 0;
        for (Project project : projects) {
            projectNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            String projectDuration = calculateDuration(project.getStartDate(), project.getEndDate());
            String projectTitle = project.getName() + " | " + projectDuration + " | " + project.getStatus();

            createBodyRun(bodyParagraph, projectTitle, true);
            insertNewLine(bodyParagraph);
            if (project.getReferenceLink() != null) {
                createHyperlinkRun(bodyParagraph, project.getReferenceLink(), "Click to open the project");
                insertNewLine(bodyParagraph);
            }

            if (project.getDescription() != null) {
                createBodyRun(bodyParagraph, project.getDescription(), false);
                insertNewLine(bodyParagraph);
            }

            if (projectNumber < projects.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addCoursesToDocument(XWPFDocument document, List<Course> courses) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Courses", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (Course course : courses) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            createBodyRun(bodyParagraph, course.getName() + " | " + course.getInstitute(), false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addSkillsToDocument(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Skills", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        if (hardSkills != null) {
            for (HardSkill hardSkill : hardSkills) {
                bodyParagraph.setIndentationLeft(INDENTATION);
                createBodyRun(bodyParagraph, hardSkill.getType().toString(), false);
                addBulletToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            }
        }

        if (softSkills != null) {
            for (SoftSkill softSkill : softSkills) {
                bodyParagraph.setIndentationLeft(INDENTATION);
                createBodyRun(bodyParagraph, softSkill.getTitle(), false);
                addBulletToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            }
        }

        insertNewLine(bodyParagraph);
    }

    private static void addFormerColleaguesToDocument(XWPFDocument document, List<FormerColleague> formerColleagues) {
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Former Colleagues", TITLE_SIZE);

        XWPFParagraph bodyParagraph = document.createParagraph();
        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (FormerColleague formerColleague : formerColleagues) {
            bodyParagraph.setIndentationLeft(300);
            addDashToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);
            createBodyRun(bodyParagraph, formerColleague.getFullName(), false);
            addBulletToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);

            createBodyRun(bodyParagraph, formerColleague.getPosition(), false);
            addBulletToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR);

            createBodyRun(bodyParagraph, formerColleague.getPhoneNumber(), false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addJobExperiencesToDocument(XWPFDocument document, List<JobExperience> jobExperiences) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        createTitleRun(titleParagraph, "Experiences", TITLE_SIZE);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
        bodyParagraph.setIndentationLeft(INDENTATION);

        int jobExperienceNumber = 0;
        for (JobExperience jobExperience : jobExperiences) {
            jobExperienceNumber++;
            createBodyRun(bodyParagraph, calculateDuration(jobExperience.getStartDate(), jobExperience.getEndDate()), false);
            insertNewLine(bodyParagraph);

            createBodyRun(bodyParagraph, extractJobTitle(jobExperience), true);
            insertNewLine(bodyParagraph);

            createBodyRun(bodyParagraph, extractJobDescription(jobExperience), false);
            insertNewLine(bodyParagraph);

            if (jobExperienceNumber < jobExperiences.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        createBodyRun(paragraph, summary.getText(), true);
        insertNewLine(paragraph);
    }

    private static void addContactInformation(XWPFDocument document, List<ContactMethod> contactInformation) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);

        for (ContactMethod contactMethod : contactInformation) {
            createBodyRun(paragraph, contactMethod.getContent(), false);
            addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
        }

        insertNewLine(paragraph);
    }

    private static void createNameTitle(XWPFDocument document, PersonalInformation personalInformation) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        createTitleRun(paragraph, personalInformation.getFullName(), 35);
    }

    private static String extractEducationTitle(Education education) {
        return education.getMajor() + " | " + education.getUniversity() + " | " + education.getDegreeLevel();
    }

    private static String calculateEducationDuration(Education education) {
        String startDate = String.valueOf(education.getStartYear());
        String endDate = education.getEndYear() == 0 ? "Today" : String.valueOf(education.getStartYear());
        return startDate + " - " + endDate;
    }

    private static String extractJobDescription(JobExperience jobExperience) {
        return jobExperience.getDescription();
    }

    private static String extractJobTitle(JobExperience jobExperience) {
        return jobExperience.getTitle() +
                " | " +
                jobExperience.getCompanyName() +
                " | " +
                jobExperience.getLocation().getCityName();
    }

    private static String calculateDuration(LocalDate startDate, LocalDate endDate) {
        String start = (startDate.getMonth().toString()).substring(0, 3) + " " + startDate.getYear();
        String end = endDate == null ? "Today" : (endDate.getMonth().toString()).substring(0, 3) + " " + endDate.getYear();
        return start + " - " + end;
    }
}
