package com.mmf.resumeBuilder.service.wordtools.documentgenerator;

import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.entity.resume.*;
import com.mmf.resumeBuilder.service.wordtools.FontProperties;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateDuration;
import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateYearDuration;
import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;

public class ClassicAccountingDocumentGenerator implements DocumentGenerator {
    private static final String DATE_COLOR = "575355";
    private static final String HEADING_COLOR = "2E471D";
    public static final String BODY_COLOR = "212010";
    public static final String BOLD_COLOR = "000000";
    private static final String SYMBOL_COLOR = "558335";
    public static final int INDENTATION = 300;
    private static final FontProperties HEADING_FONT = new FontProperties(16, HEADING_COLOR, "Gill Sans MT (Headings)");
    private static final FontProperties BODY_FONT = new FontProperties(10, BODY_COLOR, "Gill Sans MT (Body)");
    private static final FontProperties TITLE_FONT = new FontProperties(10, BOLD_COLOR, "Gill Sans MT (Headings)");
    public static final FontProperties DATE_FONT = new FontProperties(10, DATE_COLOR, "Gill Sans MT");

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
                createNameTitle(document, personalInformation, contactInformation);

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

                FileOutputStream out = new FileOutputStream(generateFilePath(personalInformation, "Classic Accounting"));
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

    private static void createNameTitle(XWPFDocument document, PersonalInformation personalInformation, List<ContactMethod> contactInformation) {
        String address = "";
        String email = "";
        String phoneNumber = "";

        if (contactInformation != null && !contactInformation.isEmpty()) {
            Optional<ContactMethod> addressInfo = contactInformation.stream().filter(c -> c.getType() == ContactType.Address).findFirst();
            if (addressInfo.isPresent())
                address = addressInfo.get().getContent();

            Optional<ContactMethod> emailInfo = contactInformation.stream().filter(c -> c.getType() == ContactType.Email).findFirst();
            if (emailInfo.isPresent())
                email = emailInfo.get().getContent();

            Optional<ContactMethod> phoneNumberInfo = contactInformation.stream().filter(c -> c.getType() == ContactType.Phone_Number).findFirst();
            if (phoneNumberInfo.isPresent())
                phoneNumber = phoneNumberInfo.get().getContent();
        }

        FontProperties font = new FontProperties(20, HEADING_COLOR, "Gill Sans MT (Headings)");
        FontProperties font1 = new FontProperties(15, BOLD_COLOR, "Gill Sans MT (Body)");

        addSingleRowTableToDocument(document, asList(personalInformation.getFirstName(), "", address), font, false, 0);
        addSingleRowTableToDocument(document, asList(personalInformation.getLastName(), phoneNumber, email), font1, false, 0);
        document.createParagraph();
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
        XWPFParagraph paragraph = document.createParagraph();

        for (String summaryParagraph : summary.getText().split("\\n")) {
            addRunToParagraph(paragraph, summaryParagraph, BODY_FONT, false);
            insertNewLine(paragraph);
        }
    }

    private static void addJobExperiencesToDocument(XWPFDocument document, List<JobExperience> jobExperiences) {
        addHeadingRunToParagraph(document.createParagraph(), "Experiences", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        int jobNumber = 0;
        for (JobExperience job : jobExperiences) {
            jobNumber++;
            String jobDuration = calculateDuration(job.getStartDate(), job.getEndDate());

            addRunToParagraph(paragraph, jobDuration, DATE_FONT, false);
            insertNewLine(paragraph);

            addRunToParagraph(paragraph, job.getTitle(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');
            addRunToParagraph(paragraph, job.getCompanyName(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');
            addRunToParagraph(paragraph, job.getLocation().getCityName().toString(), TITLE_FONT, true);
            insertNewLine(paragraph);

            for (String descriptionParagraph : job.getDescription().split("\\n")) {
                addRunToParagraph(paragraph, descriptionParagraph, BODY_FONT, false);
                insertNewLine(paragraph);
            }

            if (jobNumber < jobExperiences.size())
                insertNewLine(paragraph);
        }
    }

    private static void addFormerColleaguesToDocument(XWPFDocument document, List<FormerColleague> formerColleagues) {
        addHeadingRunToParagraph(document.createParagraph(), "Former Colleagues", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (FormerColleague formerColleague : formerColleagues) {
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');

            addRunToParagraph(paragraph, formerColleague.getFullName(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');

            addRunToParagraph(paragraph, formerColleague.getPosition(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');

            addRunToParagraph(paragraph, formerColleague.getPhoneNumber(), BODY_FONT, false);
            insertNewLine(paragraph);
        }
    }

    private static void addSkillsToDocument(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        addHeadingRunToParagraph(document.createParagraph(), "Skills", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);
        List<String> skills = new LinkedList<>();

        if (hardSkills != null) {
            skills = hardSkills.stream().map(h -> h.getType().toString()).toList();
        }

        if (softSkills != null) {
            List<String> softSkillList = softSkills.stream().map(SoftSkill::getTitle).toList();
            skills = Stream.concat(skills.stream(), softSkillList.stream()).toList();
        }

        int skillNumber = 0;
        for (String skill : skills) {
            skillNumber++;
            addRunToParagraph(paragraph, skill, BODY_FONT, false);

            if (skillNumber < skills.size()) {
                addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');
            }
        }

        insertNewLine(paragraph);
    }

    private static void addCoursesToDocument(XWPFDocument document, List<Course> courses) {
        addHeadingRunToParagraph(document.createParagraph(), "Courses", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (Course course : courses) {
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');

            addRunToParagraph(paragraph, course.getName(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(paragraph, course.getInstitute(), BODY_FONT, false);
            insertNewLine(paragraph);
        }
    }

    private static void addProjectsToDocument(XWPFDocument document, List<Project> projects) {
        addHeadingRunToParagraph(document.createParagraph(), "Projects", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        int projectNumber = 0;
        for (Project project : projects) {
            projectNumber++;
            addHyperlinkRunToParagraph(paragraph, project.getReferenceLink(), project.getName(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            String projectDuration = calculateDuration(project.getStartDate(), project.getEndDate());
            addHyperlinkRunToParagraph(paragraph, project.getReferenceLink(), projectDuration, TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addHyperlinkRunToParagraph(paragraph, project.getReferenceLink(), project.getStatus().toString(), TITLE_FONT, true);
            insertNewLine(paragraph);

            for (String descriptionParagraph : project.getDescription().split("\\n")) {
                addRunToParagraph(paragraph, descriptionParagraph, BODY_FONT, false);
                insertNewLine(paragraph);
            }

            if (projectNumber < projects.size())
                insertNewLine(paragraph);
        }
    }

    private static void addEducationsToDocument(XWPFDocument document, List<Education> educations) {
        addHeadingRunToParagraph(document.createParagraph(), "Education", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (Education education : educations) {
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');
            addRunToParagraph(paragraph, education.getUniversity(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');

            addRunToParagraph(paragraph, education.getDegreeLevel().toString(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');

            String educationDuration = calculateYearDuration(education.getStartYear(), education.getEndYear());
            addRunToParagraph(paragraph, educationDuration, BODY_FONT, false);

            insertNewLine(paragraph);
        }
    }

    private static void addTeachingAssistanceToDocument(XWPFDocument document, List<TeachingAssistance> teachingAssistanceList) {
        addHeadingRunToParagraph(document.createParagraph(), "Teaching Assistance", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (TeachingAssistance ta : teachingAssistanceList) {
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');
            addRunToParagraph(paragraph, ta.getTitle(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '•');

            addRunToParagraph(paragraph, ta.getUniversity(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '•');

            addRunToParagraph(paragraph, calculateDuration(ta.getStartDate(), ta.getEndDate()), BODY_FONT, false);
            insertNewLine(paragraph);
        }
    }

    private static void addPresentationsToDocument(XWPFDocument document, List<Presentation> presentations) {
        addHeadingRunToParagraph(document.createParagraph(), "Presentations", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        int presentationNumber = 0;
        for (Presentation presentation : presentations) {
            presentationNumber++;
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '-');
            addRunToParagraph(paragraph, presentation.getTitle(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(paragraph, presentation.getDate().toString(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');
            insertNewLine(paragraph);

            addRunToParagraph(paragraph, "   " + presentation.getDescription(), BODY_FONT, false);
            insertNewLine(paragraph);

            if (presentationNumber < presentations.size())
                insertNewLine(paragraph);
        }
    }

    private static void addPatentsToDocument(XWPFDocument document, List<Patent> patents) {
        addHeadingRunToParagraph(document.createParagraph(), "Patents", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        int patentNumber = 0;
        for (Patent patent : patents) {
            patentNumber++;
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '-');
            addRunToParagraph(paragraph, patent.getTitle(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(paragraph, patent.getRegistrationNumber(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(paragraph, patent.getRegistrationDate().toString(), TITLE_FONT, true);
            insertNewLine(paragraph);

            addRunToParagraph(paragraph, "   " + patent.getDescription(), TITLE_FONT, false);
            insertNewLine(paragraph);

            if (patentNumber < patents.size())
                insertNewLine(paragraph);
        }
    }

    private static void addResearchesToDocument(XWPFDocument document, List<Research> researches) {
        addHeadingRunToParagraph(document.createParagraph(), "Researches", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        int researchNumber = 0;
        for (Research research : researches) {
            researchNumber++;
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '-');
            addHyperlinkRunToParagraph(paragraph, research.getReferenceLink(), research.getTitle(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addHyperlinkRunToParagraph(paragraph, research.getReferenceLink(), research.getPublisher(), TITLE_FONT, true);
            addSymbolToParagraph(paragraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addHyperlinkRunToParagraph(paragraph, research.getReferenceLink(), research.getDate() + " (Click to open the research)", TITLE_FONT, true);
            insertNewLine(paragraph);

            addRunToParagraph(paragraph, "   " + research.getDescription(), BODY_FONT, false);
            insertNewLine(paragraph);

            if (researchNumber < researches.size())
                insertNewLine(paragraph);
        }
    }

    private static void addLanguagesToDocument(XWPFDocument document, List<Language> languages) {
        addHeadingRunToParagraph(document.createParagraph(), "Languages", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        int languageNumber = 0;
        for (Language language : languages) {
            languageNumber++;
            addRunToParagraph(paragraph, language.getName().toString(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, ':');
            addRunToParagraph(paragraph, language.estimateAverageLevel().toString(), BODY_FONT, false);

            if (languageNumber < languages.size())
                addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');
        }

        insertNewLine(paragraph);
    }

    private static void addHobbiesToDocument(XWPFDocument document, List<Hobby> hobbies) {
        addHeadingRunToParagraph(document.createParagraph(), "Hobbies", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        int hobbyNumber = 0;
        for (Hobby hobby : hobbies) {
            hobbyNumber++;
            addRunToParagraph(paragraph, hobby.getTitle(), BODY_FONT, false);

            if (hobbyNumber < hobbies.size())
                addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');
        }

        insertNewLine(paragraph);
    }

    private static void addMembershipsToDocument(XWPFDocument document, List<Membership> memberships) {
        addHeadingRunToParagraph(document.createParagraph(), "Memberships", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (Membership membership : memberships) {
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');
            addRunToParagraph(paragraph, membership.getTitle(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(paragraph, valueOf(membership.getDate().getYear()), BODY_FONT, false);
            insertNewLine(paragraph);
        }
    }

    private static void addVolunteerActivitiesToDocument(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
        addHeadingRunToParagraph(document.createParagraph(), "Volunteer Activities", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (VolunteerActivity activity : volunteerActivities) {
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');
            addRunToParagraph(paragraph, activity.getTitle(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(paragraph, valueOf(activity.getYear()), BODY_FONT, false);
            insertNewLine(paragraph);
        }
    }
}
