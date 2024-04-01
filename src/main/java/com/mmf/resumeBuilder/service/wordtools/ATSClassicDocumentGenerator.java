package com.mmf.resumeBuilder.service.wordtools;

import com.mmf.resumeBuilder.entity.resume.*;
import com.mmf.resumeBuilder.service.datetools.DateCalculation;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;

public class ATSClassicDocumentGenerator implements DocumentGenerator {
    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    public static final int INDENTATION = 300;
    private static final FontProperties nameTitleFont = new FontProperties(35, "262626", "Speak Pro (Headings)");
    private static final FontProperties sectionTitleFont = new FontProperties(16, "262626", "Speak Pro (Headings)");
    public static final int BODY_SIZE = 10;
    public static final String BULLET_COLOR = "D195A9";
    public static final String FILL_COLOR = "F6EAEE";
    private static final FontProperties bodyFont = new FontProperties(10, "5A5A5A", "Times New Roman (Headings CS)");

    @Override
    public void generateWordDocument(Resume resume) {
        try {
            XWPFDocument document = createDocument(0.5, 0.5, 0.5, 0.5);

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

                FileOutputStream out = new FileOutputStream(generateFilePath(personalInformation));
                document.write(out);
                out.close();
                document.close();
            } else {
                throw new IOException("Contact information can not be empty");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createNameTitle(XWPFDocument document, PersonalInformation personalInformation) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        addHeadingRunToParagraph(paragraph, personalInformation.getFullName(), nameTitleFont, FILL_COLOR);
    }

    private static void addContactInformation(XWPFDocument document, List<ContactMethod> contactInformation) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        addSymbolToParagraph(paragraph, BODY_SIZE, BULLET_COLOR, '•');

        for (ContactMethod contactMethod : contactInformation) {
            addRunToParagraph(paragraph, contactMethod.getContent(), bodyFont, false);
            addSymbolToParagraph(paragraph, BODY_SIZE, BULLET_COLOR, '•');
        }

        insertNewLine(paragraph);
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        for (String summaryParagraph : summary.getText().split("\\n")) {
            addRunToParagraph(paragraph, summaryParagraph, bodyFont, true);
            insertNewLine(paragraph);
        }
    }

    private static void addJobExperiencesToDocument(XWPFDocument document, List<JobExperience> jobExperiences) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Experiences", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
        bodyParagraph.setIndentationLeft(INDENTATION);

        int jobExperienceNumber = 0;
        for (JobExperience jobExperience : jobExperiences) {
            jobExperienceNumber++;
            addRunToParagraph(bodyParagraph, DateCalculation.calculateDuration(jobExperience.getStartDate(), jobExperience.getEndDate()), bodyFont, false);
            insertNewLine(bodyParagraph);

            String jobTitle = jobExperience.getTitle() +
                    " | " +
                    jobExperience.getCompanyName() +
                    " | " +
                    jobExperience.getLocation().getCityName();
            addRunToParagraph(bodyParagraph, jobTitle, bodyFont, true);
            insertNewLine(bodyParagraph);

            for (String descriptionParagraph : jobExperience.getDescription().split("\\n")) {
                addRunToParagraph(bodyParagraph, descriptionParagraph, bodyFont, false);
                insertNewLine(bodyParagraph);
            }

            if (jobExperienceNumber < jobExperiences.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addFormerColleaguesToDocument(XWPFDocument document, List<FormerColleague> formerColleagues) {
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Former Colleagues", sectionTitleFont, FILL_COLOR);

        XWPFParagraph bodyParagraph = document.createParagraph();
        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (FormerColleague formerColleague : formerColleagues) {
            bodyParagraph.setIndentationLeft(300);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            addRunToParagraph(bodyParagraph, formerColleague.getFullName(), bodyFont, false);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '•');

            addRunToParagraph(bodyParagraph, formerColleague.getPosition(), bodyFont, false);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '•');

            addRunToParagraph(bodyParagraph, formerColleague.getPhoneNumber(), bodyFont, false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addSkillsToDocument(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Skills", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        if (hardSkills != null) {
            for (HardSkill hardSkill : hardSkills) {
                bodyParagraph.setIndentationLeft(INDENTATION);
                addRunToParagraph(bodyParagraph, hardSkill.getType().toString(), bodyFont, false);
                addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '•');
            }
        }

        if (softSkills != null) {
            for (SoftSkill softSkill : softSkills) {
                bodyParagraph.setIndentationLeft(INDENTATION);
                addRunToParagraph(bodyParagraph, softSkill.getTitle(), bodyFont, false);
                addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '•');
            }
        }

        insertNewLine(bodyParagraph);
    }

    private static void addCoursesToDocument(XWPFDocument document, List<Course> courses) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Courses", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (Course course : courses) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            addRunToParagraph(bodyParagraph, course.getName() + " | " + course.getInstitute(), bodyFont, false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addProjectsToDocument(XWPFDocument document, List<Project> projects) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Projects", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        int projectNumber = 0;
        for (Project project : projects) {
            projectNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            String projectDuration = DateCalculation.calculateDuration(project.getStartDate(), project.getEndDate());
            String projectTitle = project.getName() + " | " + projectDuration + " | " + project.getStatus();

            addRunToParagraph(bodyParagraph, projectTitle, bodyFont, true);
            insertNewLine(bodyParagraph);
            if (project.getReferenceLink() != null) {
                addHyperlinkRunToParagraph(bodyParagraph, project.getReferenceLink(), "Click to open the project", bodyFont, false);
                insertNewLine(bodyParagraph);
            }

            if (project.getDescription() != null) {
                for (String descriptionParagraph : project.getDescription().split("\\n")) {
                    addRunToParagraph(bodyParagraph, descriptionParagraph, bodyFont, false);
                    insertNewLine(bodyParagraph);
                }
            }

            if (projectNumber < projects.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addEducationsToDocument(XWPFDocument document, List<Education> educations) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Education", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
        int educationNumber = 0;
        for (Education education : educations) {
            educationNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addRunToParagraph(bodyParagraph, DateCalculation.calculateYearDuration(education.getStartYear(), education.getEndYear()), bodyFont, false);
            insertNewLine(bodyParagraph);

            String educationTitle = education.getMajor() + " | " + education.getUniversity() + " | " + education.getDegreeLevel();
            addRunToParagraph(bodyParagraph, educationTitle, bodyFont, true);
            insertNewLine(bodyParagraph);

            if (educationNumber < educations.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addTeachingAssistanceToDocument(XWPFDocument document, List<TeachingAssistance> teachingAssistance) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Teaching Assistance", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (TeachingAssistance ta : teachingAssistance) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            String teachingAssistanceTitle = ta.getTitle() + " | " + ta.getUniversity() + " | " + DateCalculation.calculateDuration(ta.getStartDate(), ta.getEndDate());
            addRunToParagraph(bodyParagraph, teachingAssistanceTitle, bodyFont, false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addPresentationsToDocument(XWPFDocument document, List<Presentation> presentations) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Presentations", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        int presentationNumber = 0;
        for (Presentation presentation : presentations) {
            presentationNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            String presentationTitle = presentation.getTitle() + " | " + presentation.getDate();
            addRunToParagraph(bodyParagraph, presentationTitle, bodyFont, true);
            insertNewLine(bodyParagraph);

            if (presentation.getReferenceLink() != null) {
                addHyperlinkRunToParagraph(bodyParagraph, presentation.getReferenceLink(), "More about the presentation", bodyFont, false);
                insertNewLine(bodyParagraph);
            }

            if (presentation.getDescription() != null) {
                addRunToParagraph(bodyParagraph, presentation.getDescription(), bodyFont, false);
                insertNewLine(bodyParagraph);
            }

            if (presentationNumber < presentations.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addPatentsToDocument(XWPFDocument document, List<Patent> patents) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Patents", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        int patentNumber = 0;
        for (Patent patent : patents) {
            patentNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            String presentationTitle = patent.getTitle() + " | " + patent.getRegistrationNumber() + " | " + patent.getRegistrationDate();
            addRunToParagraph(bodyParagraph, presentationTitle, bodyFont, true);
            insertNewLine(bodyParagraph);

            if (patent.getReferenceLink() != null) {
                addHyperlinkRunToParagraph(bodyParagraph, patent.getReferenceLink(), "More about the patent", bodyFont, false);
                insertNewLine(bodyParagraph);
            }

            if (patent.getDescription() != null) {
                addRunToParagraph(bodyParagraph, patent.getDescription(), bodyFont, false);
                insertNewLine(bodyParagraph);
            }

            if (patentNumber < patents.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addResearchesToDocument(XWPFDocument document, List<Research> researches) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Researches", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        int researchNumber = 0;
        for (Research research : researches) {
            researchNumber++;
            bodyParagraph.setIndentationLeft(INDENTATION);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            String presentationTitle = research.getTitle() + " | " + research.getPublisher() + " | " + research.getDate();
            addRunToParagraph(bodyParagraph, presentationTitle, bodyFont, true);
            insertNewLine(bodyParagraph);

            if (research.getReferenceLink() != null) {
                addHyperlinkRunToParagraph(bodyParagraph, research.getReferenceLink(), "Research link", bodyFont, false);
                insertNewLine(bodyParagraph);
            }

            if (research.getDescription() != null) {
                for (String researchParagraph : research.getDescription().split("\\n")) {
                    addRunToParagraph(bodyParagraph, researchParagraph, bodyFont, false);
                    insertNewLine(bodyParagraph);
                }
            }

            if (researchNumber < researches.size())
                insertNewLine(bodyParagraph);
        }
    }

    private static void addLanguagesToDocument(XWPFDocument document, List<Language> languages) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Languages", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (Language language : languages) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            String presentationTitle = language.getName() + ": " + language.estimateAverageLevel();
            addRunToParagraph(bodyParagraph, presentationTitle, bodyFont, false);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '•');
        }

        insertNewLine(bodyParagraph);
    }

    private static void addHobbiesToDocument(XWPFDocument document, List<Hobby> hobbies) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Hobbies", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (Hobby hobby : hobbies) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addRunToParagraph(bodyParagraph, hobby.getTitle(), bodyFont, false);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '•');
        }

        insertNewLine(bodyParagraph);
    }

    private static void addMembershipsToDocument(XWPFDocument document, List<Membership> memberships) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Memberships", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (Membership membership : memberships) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            String presentationTitle = membership.getTitle() + " | " + membership.getDate().getYear();
            addRunToParagraph(bodyParagraph, presentationTitle, bodyFont, false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addVolunteerActivitiesToDocument(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Volunteer Activities", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        for (VolunteerActivity volunteerActivity : volunteerActivities) {
            bodyParagraph.setIndentationLeft(INDENTATION);
            addSymbolToParagraph(bodyParagraph, BODY_SIZE, BULLET_COLOR, '-');
            addRunToParagraph(bodyParagraph, volunteerActivity.getTitle() + " | " + volunteerActivity.getYear(), bodyFont, false);
            insertNewLine(bodyParagraph);
        }
    }

    private static String generateFilePath(PersonalInformation personalInformation) {
        return STORE_PATH +
                personalInformation.getFullName() +
                " _ " +
                LocalDate.now() +
                " _ " +
                LocalTime.now().getHour() + "-" + LocalTime.now().getMinute() + "-" + LocalTime.now().getSecond() +
                ".docx";
    }
}
