package com.mmf.resumeBuilder.service.tools.word.documentgenerator;

import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.entity.resume.*;
import com.mmf.resumeBuilder.service.tools.word.FontProperties;
import com.mmf.resumeBuilder.service.tools.word.Symbol;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static com.mmf.resumeBuilder.service.tools.date.DateCalculation.calculateDuration;
import static com.mmf.resumeBuilder.service.tools.date.DateCalculation.calculateYearDuration;
import static com.mmf.resumeBuilder.service.tools.word.WordProcessing.*;
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
    private static final Symbol BULLET = new Symbol(SYMBOL_COLOR, '•');
    private static final Symbol DASH = new Symbol(SYMBOL_COLOR, '-');
    private static final Symbol COLON = new Symbol(SYMBOL_COLOR, ':');
    private static final Symbol PIPE = new Symbol(SYMBOL_COLOR, '|');
    private static final Symbol NEW_LINE = new Symbol(SYMBOL_COLOR, '\n');

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

                FileOutputStream out = new FileOutputStream(generateFilePath(personalInformation, ResumeTheme.Classic_Accounting));
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
        var split = Arrays.stream(summary.getText().split("\\n")).toList();
        addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, false);
        insertNewLine(paragraph);
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

            List<String> titleParts = Arrays.asList(job.getTitle(), job.getCompanyName(), job.getLocation().getCity());
            addRunToParagraph(paragraph, titleParts, TITLE_FONT, PIPE, true);
            insertNewLine(paragraph);

            var split = Arrays.stream(job.getDescription().split("\\n")).toList();
            addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, false);
            insertNewLine(paragraph);

            if (jobNumber < jobExperiences.size())
                insertNewLine(paragraph);
        }
    }

    private static void addFormerColleaguesToDocument(XWPFDocument document, List<FormerColleague> formerColleagues) {
        addHeadingRunToParagraph(document.createParagraph(), "Former Colleagues", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (FormerColleague formerColleague : formerColleagues) {
            addSymbolToParagraph(paragraph, DASH, BODY_FONT.getSize());

            List<String> titleParts = Arrays.asList(formerColleague.getFullName(), formerColleague.getPosition(), formerColleague.getPhoneNumber());
            addRunToParagraph(paragraph, titleParts, BODY_FONT, BULLET, false);
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

        addRunToParagraph(paragraph, skills, BODY_FONT, BULLET, false);
        insertNewLine(paragraph);
    }

    private static void addCoursesToDocument(XWPFDocument document, List<Course> courses) {
        addHeadingRunToParagraph(document.createParagraph(), "Courses", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (Course course : courses) {
            addSymbolToParagraph(paragraph, DASH, BODY_FONT.getSize());
            List<String> titleParts = Arrays.asList(course.getName(), course.getInstitute());

            addRunToParagraph(paragraph, titleParts, BODY_FONT, PIPE, false);
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
            String projectDuration = calculateDuration(project.getStartDate(), project.getEndDate());

            Map<String, String> titleParts = new LinkedHashMap<>() {{
                put(project.getName(), project.getReferenceLink());
                put(projectDuration, project.getReferenceLink());
                put(project.getStatus().toString(), project.getReferenceLink());
            }};
            addHyperlinkRunToParagraph(paragraph, titleParts, TITLE_FONT, PIPE, true);

            insertNewLine(paragraph);

            var split = Arrays.stream(project.getDescription().split("\\n")).toList();
            addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, false);
            insertNewLine(paragraph);

            if (projectNumber < projects.size())
                insertNewLine(paragraph);
        }
    }

    private static void addEducationsToDocument(XWPFDocument document, List<Education> educations) {
        addHeadingRunToParagraph(document.createParagraph(), "Education", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (Education education : educations) {
            addSymbolToParagraph(paragraph, DASH, BODY_FONT.getSize());
            String educationDuration = calculateYearDuration(education.getStartYear(), education.getEndYear());

            List<String> titleParts = Arrays.asList(education.getMajor().toString(),
                    education.getUniversity(),
                    education.getDegreeLevel().toString(),
                    educationDuration);
            addRunToParagraph(paragraph, titleParts, BODY_FONT, BULLET, false);
            insertNewLine(paragraph);
        }
    }

    private static void addTeachingAssistanceToDocument(XWPFDocument document, List<TeachingAssistance> teachingAssistanceList) {
        addHeadingRunToParagraph(document.createParagraph(), "Teaching Assistance", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (TeachingAssistance ta : teachingAssistanceList) {
            addSymbolToParagraph(paragraph, DASH, BODY_FONT.getSize());
            String duration = calculateDuration(ta.getStartDate(), ta.getEndDate());
            List<String> titleParts = Arrays.asList(ta.getTitle(), ta.getUniversity(), duration);

            addRunToParagraph(paragraph, titleParts, TITLE_FONT, BULLET, false);
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
            List<String> titleParts = Arrays.asList(presentation.getTitle(), presentation.getDate().toString());

            addSymbolToParagraph(paragraph, DASH, TITLE_FONT.getSize());
            addRunToParagraph(paragraph, titleParts, TITLE_FONT, PIPE, true);
            insertNewLine(paragraph);

            var split = Arrays.stream(presentation.getDescription().split("\\n")).toList();
            addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, false);
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
            addSymbolToParagraph(paragraph, DASH, TITLE_FONT.getSize());

            List<String> titleParts = Arrays.asList(patent.getTitle(),
                    patent.getRegistrationNumber(),
                    patent.getRegistrationDate().toString());
            addRunToParagraph(paragraph, titleParts, TITLE_FONT, PIPE, true);
            insertNewLine(paragraph);

            var split = Arrays.stream(patent.getDescription().split("\\n")).toList();
            addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, false);
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
            Map<String, String> links = new LinkedHashMap<>() {{
                put(research.getTitle(), research.getReferenceLink());
                put(research.getPublisher(), research.getReferenceLink());
                put(research.getDate() + " (Click to open the research)", research.getReferenceLink());
            }};

            addSymbolToParagraph(paragraph, DASH, TITLE_FONT.getSize());
            addHyperlinkRunToParagraph(paragraph, links, TITLE_FONT, PIPE, true);
            insertNewLine(paragraph);

            var split = Arrays.stream(research.getDescription().split("\\n")).toList();
            addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, false);
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
            addSymbolToParagraph(paragraph, COLON, BODY_FONT.getSize());
            addRunToParagraph(paragraph, language.estimateAverageLevel().toString(), BODY_FONT, false);

            if (languageNumber < languages.size())
                addSymbolToParagraph(paragraph, BULLET, BODY_FONT.getSize());
        }

        insertNewLine(paragraph);
    }

    private static void addHobbiesToDocument(XWPFDocument document, List<Hobby> hobbies) {
        addHeadingRunToParagraph(document.createParagraph(), "Hobbies", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        List<String> hobbyList = new LinkedList<>();
        for (Hobby hobby : hobbies) {
            hobbyList.add(hobby.getTitle());
        }

        addRunToParagraph(paragraph, hobbyList, BODY_FONT, BULLET, false);
        insertNewLine(paragraph);
    }

    private static void addMembershipsToDocument(XWPFDocument document, List<Membership> memberships) {
        addHeadingRunToParagraph(document.createParagraph(), "Memberships", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (Membership membership : memberships) {
            addSymbolToParagraph(paragraph, DASH, BODY_FONT.getSize());
            List<String> titleParts = Arrays.asList(membership.getTitle(), valueOf(membership.getDate().getYear()));

            addRunToParagraph(paragraph, titleParts, BODY_FONT, PIPE, false);
            insertNewLine(paragraph);
        }
    }

    private static void addVolunteerActivitiesToDocument(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
        addHeadingRunToParagraph(document.createParagraph(), "Volunteer Activities", HEADING_FONT, "");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setIndentationLeft(INDENTATION);

        for (VolunteerActivity activity : volunteerActivities) {
            addSymbolToParagraph(paragraph, DASH, BODY_FONT.getSize());
            List<String> titleParts = Arrays.asList(activity.getTitle(), valueOf(activity.getYear()));

            addRunToParagraph(paragraph, titleParts, BODY_FONT, PIPE, false);
            insertNewLine(paragraph);
        }
    }
}
