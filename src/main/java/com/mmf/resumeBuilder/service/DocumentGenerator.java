package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.entity.resume.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;

public class DocumentGenerator {

    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    public static final int INDENTATION = 300;

    ///////////////////////////////// Generate word documents using Java code
    public static void createNewWordDocument(Resume resume) {
        try {
            XWPFDocument document = new XWPFDocument();

            createNameTitle(document, resume);

            addContactInformation(document, resume.getContactInformation());

            addSummary(document, resume.getSummary());

            createExperiencesTitle(document, resume.getJobExperiences());
            addJobExperiences(document, resume.getJobExperiences());

            createFormerColleaguesTitle(document, resume.getFormerColleagues());
            addFormerColleagues(document, resume.getFormerColleagues());

            createSkillsTitle(document, resume.getHardSkills(), resume.getSoftSkills());
            addSkills(document, resume.getHardSkills(), resume.getSoftSkills());

            createCoursesTitle(document, resume.getCourses());
            addCourses(document, resume.getCourses());

            createProjectsTitle(document, resume.getProjects());
            addProjects(document, resume.getProjects());

            createEducationTitle(document, resume.getEducations());
            addEducations(document, resume.getEducations());

            createTeachingAssistanceTitle(document, resume.getTeachingAssistance());
            addTeachingAssistance(document, resume.getTeachingAssistance());

            createPresentationsTitle(document, resume.getPresentations());
            addPresentations(document, resume.getPresentations());

            createPatentsTitle(document, resume.getPatents());
            addPatents(document, resume.getPatents());

            createResearchesTitle(document, resume.getResearches());
            addResearches(document, resume.getResearches());

            createLanguagesTitle(document, resume.getLanguages());
            addLanguages(document, resume.getLanguages());

            createHobbiesTitle(document, resume.getHobbies());
            addHobbies(document, resume.getHobbies());

            createMembershipsTitle(document, resume.getMemberships());
            addMemberships(document, resume.getMemberships());

            createVolunteerActivitiesTitle(document, resume.getVolunteerActivities());
            addVolunteerActivities(document, resume.getVolunteerActivities());

            FileOutputStream out = new FileOutputStream(STORE_PATH + "NameTest.docx");
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addVolunteerActivities(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
        if (volunteerActivities != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            for (VolunteerActivity volunteerActivity : volunteerActivities) {
                paragraph.setIndentationLeft(INDENTATION);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                createBodyRun(paragraph, volunteerActivity.getTitle() + " | " + volunteerActivity.getYear(), false);
                insertNewLine(paragraph);
            }
        }
    }

    private static void createVolunteerActivitiesTitle(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
        if (volunteerActivities != null && !volunteerActivities.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Volunteer Activities", TITLE_SIZE);
        }
    }

    private static void addMemberships(XWPFDocument document, List<Membership> memberships) {
        if (memberships != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            for (Membership membership : memberships) {
                paragraph.setIndentationLeft(INDENTATION);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                String presentationTitle = membership.getTitle() + " | " + membership.getDate();
                createBodyRun(paragraph, presentationTitle, false);
                insertNewLine(paragraph);
            }
        }
    }

    private static void createMembershipsTitle(XWPFDocument document, List<Membership> memberships) {
        if (memberships != null && !memberships.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Memberships", TITLE_SIZE);
        }
    }

    private static void addLanguages(XWPFDocument document, List<Language> languages) {
        if (languages != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            for (Language language : languages) {
                paragraph.setIndentationLeft(INDENTATION);
                String presentationTitle = language.getName() + " - " + language.estimateAverageLevel();
                createBodyRun(paragraph, presentationTitle, false);
                addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
            }

            insertNewLine(paragraph);
        }
    }

    private static void createLanguagesTitle(XWPFDocument document, List<Language> languages) {
        if (languages != null && !languages.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Languages", TITLE_SIZE);
        }
    }

    private static void addResearches(XWPFDocument document, List<Research> researches) {
        if (researches != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            int researchNumber = 0;
            for (Research research : researches) {
                researchNumber++;
                paragraph.setIndentationLeft(INDENTATION);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                String presentationTitle = research.getTitle() + " | " + research.getPublisher() + " | " + research.getDate();
                createBodyRun(paragraph, presentationTitle, true);
                insertNewLine(paragraph);

                if (research.getReferenceLink() != null) {
                    createHyperlinkRun(paragraph, research.getReferenceLink(), "Research link");
                    insertNewLine(paragraph);
                }

                if (research.getDescription() != null) {
                    createBodyRun(paragraph, research.getDescription(), false);
                    insertNewLine(paragraph);
                }

                if (researchNumber < researches.size())
                    insertNewLine(paragraph);
            }
        }
    }

    private static void createResearchesTitle(XWPFDocument document, List<Research> researches) {
        if (researches != null && !researches.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Researches", TITLE_SIZE);
        }
    }

    private static void addPatents(XWPFDocument document, List<Patent> patents) {
        if (patents != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            int patentNumber = 0;
            for (Patent patent : patents) {
                patentNumber++;
                paragraph.setIndentationLeft(INDENTATION);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                String presentationTitle = patent.getTitle() + " | " + patent.getRegistrationNumber() + " | " + patent.getRegistrationDate();
                createBodyRun(paragraph, presentationTitle, true);
                insertNewLine(paragraph);

                if (patent.getReferenceLink() != null) {
                    createHyperlinkRun(paragraph, patent.getReferenceLink(), "More about the patent");
                    insertNewLine(paragraph);
                }

                if (patent.getDescription() != null) {
                    createBodyRun(paragraph, patent.getDescription(), false);
                    insertNewLine(paragraph);
                }

                if (patentNumber < patents.size())
                    insertNewLine(paragraph);
            }
        }
    }

    private static void createPatentsTitle(XWPFDocument document, List<Patent> patents) {
        if (patents != null && !patents.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Patents", TITLE_SIZE);
        }
    }

    private static void addPresentations(XWPFDocument document, List<Presentation> presentations) {
        if (presentations != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            int presentationNumber = 0;
            for (Presentation presentation : presentations) {
                presentationNumber++;
                paragraph.setIndentationLeft(INDENTATION);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                String presentationTitle = presentation.getTitle() + " | " + presentation.getDate();
                createBodyRun(paragraph, presentationTitle, true);
                insertNewLine(paragraph);

                if (presentation.getReferenceLink() != null) {
                    createHyperlinkRun(paragraph, presentation.getReferenceLink(), "More about the presentation");
                    insertNewLine(paragraph);
                }

                if (presentation.getDescription() != null) {
                    createBodyRun(paragraph, presentation.getDescription(), false);
                    insertNewLine(paragraph);
                }

                if (presentationNumber < presentations.size())
                    insertNewLine(paragraph);
            }
        }
    }

    private static void createPresentationsTitle(XWPFDocument document, List<Presentation> presentations) {
        if (presentations != null && !presentations.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Presentations", TITLE_SIZE);
        }
    }

    private static void addTeachingAssistance(XWPFDocument document, List<TeachingAssistance> teachingAssistance) {
        if (teachingAssistance != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            for (TeachingAssistance ta : teachingAssistance) {
                paragraph.setIndentationLeft(INDENTATION);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                String teachingAssistanceTitle = ta.getTitle() + " | " + ta.getUniversity() + " | " + calculateDuration(ta.getStartDate(), ta.getEndDate());
                createBodyRun(paragraph, teachingAssistanceTitle, false);
                insertNewLine(paragraph);
            }
        }
    }

    private static void createTeachingAssistanceTitle(XWPFDocument document, List<TeachingAssistance> teachingAssistance) {
        if (teachingAssistance != null && !teachingAssistance.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Teaching Assistance", TITLE_SIZE);
        }
    }

    private static void addProjects(XWPFDocument document, List<Project> projects) {
        if (projects != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            int projectNumber = 0;
            for (Project project : projects) {
                projectNumber++;
                paragraph.setIndentationLeft(INDENTATION);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                String projectDuration = calculateDuration(project.getStartDate(), project.getEndDate());
                String projectTitle = project.getName() + " | " + projectDuration + " | " + project.getStatus();

                createBodyRun(paragraph, projectTitle, true);
                insertNewLine(paragraph);
                if (project.getReferenceLink() != null) {
                    createHyperlinkRun(paragraph, project.getReferenceLink(), "Click to open the project");
                    insertNewLine(paragraph);
                }

                if (project.getDescription() != null) {
                    createBodyRun(paragraph, project.getDescription(), false);
                    insertNewLine(paragraph);
                }

                if (projectNumber < projects.size())
                    insertNewLine(paragraph);
            }
        }
    }

    private static void createProjectsTitle(XWPFDocument document, List<Project> projects) {
        if (projects != null && !projects.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Projects", TITLE_SIZE);
        }
    }

    private static void addCourses(XWPFDocument document, List<Course> courses) {
        if (courses != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            for (Course course : courses) {
                paragraph.setIndentationLeft(INDENTATION);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                createBodyRun(paragraph, course.getName() + " | " + course.getInstitute(), false);
                insertNewLine(paragraph);
            }
        }
    }

    private static void createCoursesTitle(XWPFDocument document, List<Course> courses) {
        if (courses != null && !courses.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Courses", TITLE_SIZE);
        }
    }

    private static void addHobbies(XWPFDocument document, List<Hobby> hobbies) {
        if (hobbies != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            for (Hobby hobby : hobbies) {
                paragraph.setIndentationLeft(INDENTATION);
                createBodyRun(paragraph, hobby.getTitle(), false);
                addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
            }

            insertNewLine(paragraph);
        }
    }

    private static void createHobbiesTitle(XWPFDocument document, List<Hobby> hobbies) {
        if (hobbies != null && !hobbies.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Hobbies", TITLE_SIZE);
        }
    }

    private static void addEducations(XWPFDocument document, List<Education> educations) {
        if (educations != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            int educationNumber = 0;
            for (Education education : educations) {
                educationNumber++;
                paragraph.setIndentationLeft(INDENTATION);
                createBodyRun(paragraph, calculateEducationDuration(education), false);
                insertNewLine(paragraph);
                createBodyRun(paragraph, extractEducationTitle(education), true);
                insertNewLine(paragraph);
            }

            if (educationNumber < educations.size())
                insertNewLine(paragraph);
        }
    }

    private static void createEducationTitle(XWPFDocument document, List<Education> educations) {
        if (educations != null && !educations.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Education", TITLE_SIZE);
        }
    }

    private static void addSkills(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        if (hardSkills != null || softSkills != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            if (hardSkills != null) {
                for (HardSkill hardSkill : hardSkills) {
                    paragraph.setIndentationLeft(INDENTATION);
                    createBodyRun(paragraph, hardSkill.getType().toString(), false);
                    addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                }
            }

            if (softSkills != null) {
                for (SoftSkill softSkill : softSkills) {
                    paragraph.setIndentationLeft(INDENTATION);
                    createBodyRun(paragraph, softSkill.getTitle(), false);
                    addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                }
            }

            insertNewLine(paragraph);
        }
    }

    private static void createSkillsTitle(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        if ((hardSkills != null && !hardSkills.isEmpty()) || (softSkills != null && !softSkills.isEmpty())) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Skills", TITLE_SIZE);
        }
    }

    private static void addFormerColleagues(XWPFDocument document, List<FormerColleague> formerColleagues) {
        if (formerColleagues != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            for (FormerColleague formerColleague : formerColleagues) {
                paragraph.setIndentationLeft(300);
                addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                createBodyRun(paragraph, formerColleague.getFullName(), false);
                addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                createBodyRun(paragraph, formerColleague.getPosition(), false);
                addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                createBodyRun(paragraph, formerColleague.getPhoneNumber(), false);
                insertNewLine(paragraph);
            }
        }
    }

    private static void createFormerColleaguesTitle(XWPFDocument document, List<FormerColleague> formerColleagues) {
        if (formerColleagues != null && !formerColleagues.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Former Colleagues", TITLE_SIZE);
        }
    }

    private static void addJobExperiences(XWPFDocument document, List<JobExperience> jobExperiences) {
        if (jobExperiences != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraph.setIndentationLeft(INDENTATION);
            int jobExperienceNumber = 0;
            for (JobExperience jobExperience : jobExperiences) {
                jobExperienceNumber++;
                createBodyRun(paragraph, calculateDuration(jobExperience.getStartDate(), jobExperience.getEndDate()), false);
                insertNewLine(paragraph);
                createBodyRun(paragraph, extractJobTitle(jobExperience), true);
                insertNewLine(paragraph);
                createBodyRun(paragraph, extractJobDescription(jobExperience), false);
                insertNewLine(paragraph);

                if (jobExperienceNumber < jobExperiences.size())
                    insertNewLine(paragraph);
            }
        }
    }

    private static void createExperiencesTitle(XWPFDocument document, List<JobExperience> jobExperiences) {
        if (jobExperiences != null && !jobExperiences.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Experiences", TITLE_SIZE);
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

    private static void createNameTitle(XWPFDocument document, Resume resume) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        createTitleRun(paragraph, resume.getPersonalInformation().getFullName(), 35);
    }

    private static String extractSkillsTitle(List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        return (hardSkills == null || hardSkills.isEmpty()) && (softSkills == null || softSkills.isEmpty()) ? "" : "Skills";
    }

    private static String extractEducationTitle(Education education) {
        return education.getMajor() + " | " + education.getUniversity() + " | " + education.getDegreeLevel();
    }

    private static String calculateEducationDuration(Education education) {
        String startDate = String.valueOf(education.getStartYear());
        String endDate = education.getEndYear() == 0 ? "Today" : String.valueOf(education.getStartYear());
        return startDate + " - " + endDate;
    }

    private static String extractSkills(List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        StringBuilder skillsValue = new StringBuilder();

        for (HardSkill hardSkill : hardSkills) {
            skillsValue.append(hardSkill.getType()).append(" / ");
        }

        for (SoftSkill softSkill : softSkills) {
            skillsValue.append(softSkill.getTitle()).append(" / ");
        }

        return skillsValue.toString();
    }

    private static String extractFormerColleagues(List<FormerColleague> formerColleagues) {
        StringBuilder formerColleaguesValue = new StringBuilder();
        for (FormerColleague formerColleague : formerColleagues) {
            formerColleaguesValue.append("Name: ").append(formerColleague.getFullName()).append(" \\u000B");
            formerColleaguesValue.append("Position: ").append(formerColleague.getPosition());
            formerColleaguesValue.append("Organization: ").append(formerColleague.getOrganizationName());
            formerColleaguesValue.append("Phone Number: ").append(formerColleague.getPhoneNumber());
        }

        return formerColleaguesValue.toString();
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

    private static String extractContactMethod(List<ContactMethod> contactInformation, ContactType contactType) {
        Optional<ContactMethod> contactMethod = contactInformation.stream().filter(c -> c.getType() == contactType).findFirst();
        return contactMethod.isPresent() ? contactMethod.get().getContent() : "";
    }

    private static String generateFilePath(Resume resume) {
        return STORE_PATH +
                resume.getPersonalInformation().getFullName() +
                " _ " +
                LocalDate.now() +
                " _ " +
                LocalTime.now().getHour() + "-" + LocalTime.now().getMinute() + "-" + LocalTime.now().getSecond() +
                ".docx";
    }
}
