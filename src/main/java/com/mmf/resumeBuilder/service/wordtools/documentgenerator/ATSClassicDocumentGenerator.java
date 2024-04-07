package com.mmf.resumeBuilder.service.wordtools.documentgenerator;

import com.mmf.resumeBuilder.entity.resume.*;
import com.mmf.resumeBuilder.service.datetools.DateCalculation;
import com.mmf.resumeBuilder.service.wordtools.FontProperties;
import com.mmf.resumeBuilder.service.wordtools.Symbol;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateYearDuration;
import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;
import static java.lang.String.valueOf;

public class ATSClassicDocumentGenerator implements DocumentGenerator {
    public static final int INDENTATION = 300;
    private static final FontProperties nameTitleFont = new FontProperties(35, "262626", "Speak Pro (Headings)");
    private static final FontProperties sectionTitleFont = new FontProperties(16, "262626", "Speak Pro (Headings)");
    public static final int BODY_SIZE = 10;
    public static final String SYMBOL_COLOR = "D195A9";
    public static final String FILL_COLOR = "F6EAEE";
    private static final FontProperties BODY_FONT = new FontProperties(10, "5A5A5A", "Times New Roman (Headings CS)");
    private static final Symbol BULLET = new Symbol(SYMBOL_COLOR, '•');
    private static final Symbol DASH = new Symbol(SYMBOL_COLOR, '-');
    private static final Symbol PIPE = new Symbol(SYMBOL_COLOR, '|');
    private static final Symbol COLON = new Symbol(SYMBOL_COLOR, ':');
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

                FileOutputStream out = new FileOutputStream(generateFilePath(personalInformation, "ATS Classic"));
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

        List<String> list = new LinkedList<>();
        for (ContactMethod contactMethod : contactInformation) {
            list.add(contactMethod.getContent());
        }

        addRunToParagraph(paragraph, list, BODY_FONT, BULLET, false);
        insertNewLine(paragraph);
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        var split = Arrays.stream(summary.getText().split("\\n")).toList();

        addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, true);
        insertNewLine(paragraph);
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
            addRunToParagraph(bodyParagraph, DateCalculation.calculateDuration(jobExperience.getStartDate(), jobExperience.getEndDate()), BODY_FONT, false);
            insertNewLine(bodyParagraph);

            List<String> titleParts = Arrays.asList(jobExperience.getTitle(),
                    jobExperience.getCompanyName(),
                    jobExperience.getLocation().getCityName().toString());

            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, true);
            insertNewLine(bodyParagraph);

            var split = Arrays.stream(jobExperience.getDescription().split("\\n")).toList();

            addRunToParagraph(bodyParagraph, split, BODY_FONT, NEW_LINE, false);
            insertNewLine(bodyParagraph);

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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);

            List<String> titleParts = Arrays.asList(formerColleague.getFullName(), formerColleague.getPosition(), formerColleague.getPhoneNumber());
            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, BULLET, false);
            insertNewLine(bodyParagraph);
        }
    }

    private static void addSkillsToDocument(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        XWPFParagraph titleParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Skills", sectionTitleFont, FILL_COLOR);

        XWPFParagraph bodyParagraph = document.createParagraph();
        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
        bodyParagraph.setIndentationLeft(INDENTATION);

        List<String> skills = new LinkedList<>();

        if (hardSkills != null) {
            skills = hardSkills.stream().map(h -> h.getType().toString()).toList();
        }

        if (softSkills != null) {
            List<String> softSkillList = softSkills.stream().map(SoftSkill::getTitle).toList();
            skills = Stream.concat(skills.stream(), softSkillList.stream()).toList();
        }

        addRunToParagraph(bodyParagraph, skills, BODY_FONT, BULLET, false);
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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);
            List<String> titleParts = Arrays.asList(course.getName(), course.getInstitute());
            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, false);
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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);
            String projectDuration = DateCalculation.calculateDuration(project.getStartDate(), project.getEndDate());
            Map<String, String> links = new LinkedHashMap<>() {{
                put(project.getName(), project.getReferenceLink());
                put(projectDuration, project.getReferenceLink());
                put(project.getStatus().toString(), project.getReferenceLink());
            }};

            addHyperlinkRunToParagraph(bodyParagraph, links, BODY_FONT, PIPE, true);

            insertNewLine(bodyParagraph);
            if (project.getReferenceLink() != null) {
                addHyperlinkRunToParagraph(bodyParagraph, project.getReferenceLink(), "Click to open the project", BODY_FONT, false);
                insertNewLine(bodyParagraph);
            }

            if (project.getDescription() != null) {
                var split = Arrays.stream(project.getDescription().split("\\n")).toList();
                addRunToParagraph(bodyParagraph, split, BODY_FONT, NEW_LINE, false);
                insertNewLine(bodyParagraph);
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
            String duration = calculateYearDuration(education.getStartYear(), education.getEndYear());
            addRunToParagraph(bodyParagraph, duration, BODY_FONT, false);
            insertNewLine(bodyParagraph);

            List<String> titleParts = Arrays.asList(education.getMajor().toString(),
                    education.getUniversity(),
                    education.getDegreeLevel().toString());

            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, true);

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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);

            List<String> titleParts = Arrays.asList(ta.getTitle(),
                    ta.getUniversity(),
                    DateCalculation.calculateDuration(ta.getStartDate(), ta.getEndDate()));

            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, false);
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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);

            List<String> titleParts = Arrays.asList(presentation.getTitle(), presentation.getDate().toString());
            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, true);
            insertNewLine(bodyParagraph);

            if (presentation.getReferenceLink() != null) {
                addHyperlinkRunToParagraph(bodyParagraph, presentation.getReferenceLink(), "More about the presentation", BODY_FONT, false);
                insertNewLine(bodyParagraph);
            }

            if (presentation.getDescription() != null) {
                addRunToParagraph(bodyParagraph, presentation.getDescription(), BODY_FONT, false);
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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);

            List<String> titleParts = Arrays.asList(patent.getTitle(), patent.getRegistrationNumber(), patent.getRegistrationDate().toString());
            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, true);
            insertNewLine(bodyParagraph);

            if (patent.getReferenceLink() != null) {
                addHyperlinkRunToParagraph(bodyParagraph, patent.getReferenceLink(), "More about the patent", BODY_FONT, false);
                insertNewLine(bodyParagraph);
            }

            if (patent.getDescription() != null) {
                addRunToParagraph(bodyParagraph, patent.getDescription(), BODY_FONT, false);
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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);

            if (research.getReferenceLink() != null) {
                Map<String, String> titleParts = new LinkedHashMap<>() {{
                    put(research.getTitle(), research.getReferenceLink());
                    put(research.getPublisher(), research.getReferenceLink());
                    put(research.getDate().toString(), research.getReferenceLink());
                }};

                addHyperlinkRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, true);
                insertNewLine(bodyParagraph);
            }

            if (research.getDescription() != null) {
                var split = Arrays.stream(research.getDescription().split("\\n")).toList();
                addRunToParagraph(bodyParagraph, split, BODY_FONT, NEW_LINE, false);
                insertNewLine(bodyParagraph);
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
        bodyParagraph.setIndentationLeft(INDENTATION);

        int languageNumber = 0;
        for (Language language : languages) {
            languageNumber++;
            addRunToParagraph(bodyParagraph, language.getName().toString(), BODY_FONT, false);
            addSymbolToParagraph(bodyParagraph, COLON, BODY_FONT.getSize());

            addRunToParagraph(bodyParagraph, language.estimateAverageLevel().toString(), BODY_FONT, false);

            if (languageNumber < languages.size())
                addSymbolToParagraph(bodyParagraph, BULLET, BODY_FONT.getSize());
        }

        insertNewLine(bodyParagraph);
    }

    private static void addHobbiesToDocument(XWPFDocument document, List<Hobby> hobbies) {
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFParagraph bodyParagraph = document.createParagraph();

        titleParagraph.setAlignment(ParagraphAlignment.LEFT);
        addHeadingRunToParagraph(titleParagraph, "Hobbies", sectionTitleFont, FILL_COLOR);

        bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

        List<String> hobbyList = new LinkedList<>();
        for (Hobby hobby : hobbies) {
            hobbyList.add(hobby.getTitle());
        }

        addRunToParagraph(bodyParagraph, hobbyList, BODY_FONT, BULLET, false);
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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);
            List<String> titleParts = Arrays.asList(membership.getTitle(), valueOf(membership.getDate().getYear()));
            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, false);
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
            addSymbolToParagraph(bodyParagraph, DASH, BODY_SIZE);

            List<String> titleParts = Arrays.asList(volunteerActivity.getTitle(), valueOf(volunteerActivity.getYear()));
            addRunToParagraph(bodyParagraph, titleParts, BODY_FONT, PIPE, false);
            insertNewLine(bodyParagraph);
        }
    }
}
