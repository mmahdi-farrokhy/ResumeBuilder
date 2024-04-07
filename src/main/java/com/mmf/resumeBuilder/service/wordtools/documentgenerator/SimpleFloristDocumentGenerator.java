package com.mmf.resumeBuilder.service.wordtools.documentgenerator;

import com.mmf.resumeBuilder.entity.resume.*;
import com.mmf.resumeBuilder.service.wordtools.FontProperties;
import com.mmf.resumeBuilder.service.wordtools.Symbol;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateDuration;
import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateYearDuration;
import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;
import static java.lang.String.valueOf;

public class SimpleFloristDocumentGenerator implements DocumentGenerator {

    public static final String TWO_INCH_WIDTH = "2900";
    public static final String FIVE_INCH_WIDTH = "14400";
    private static final FontProperties NAME_HEADING_FONT = new FontProperties(26, "5C760A", "Franklin Gothic Medium (Headings)");
    private static final FontProperties HEADING_FONT = new FontProperties(10, "5C760A", "Arial (Body)");
    private static final FontProperties BODY_FONT = new FontProperties(10, "262626", "Arial (Body)");
    private static final FontProperties TITLE_FONT = new FontProperties(10, "000000", "Arial (Body)");
    private static final FontProperties DATE_FONT = new FontProperties(10, "595959", "Arial (Body)");
    private static final String SYMBOL_COLOR = "394a04";
    public static final Symbol NEW_LINE = new Symbol(SYMBOL_COLOR, '\n');
    private static final Symbol BULLET = new Symbol(SYMBOL_COLOR, '•');
    private static final Symbol DASH = new Symbol(SYMBOL_COLOR, '-');
    private static final Symbol COLON = new Symbol(SYMBOL_COLOR, ':');
    private static final Symbol PIPE = new Symbol(SYMBOL_COLOR, '|');

    @Override
    public void generateWordDocument(Resume resume) {
        try {
            XWPFDocument document = createDocument(0.2, 0.3, 0.3, 0.3);

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
                    addContactInformationToDocument(document, contactInformation);
                }

                XWPFTable table = createTable(document);

                if (summary != null) {
                    addSummary(table, summary);
                }

                if (jobExperiences != null && !jobExperiences.isEmpty()) {
                    addJobExperiencesToDocument(table, jobExperiences);
                }

                if (formerColleagues != null && !formerColleagues.isEmpty()) {
                    addFormerColleaguesToDocument(table, formerColleagues);
                }

                if ((hardSkills != null && !hardSkills.isEmpty()) || (softSkills != null && !softSkills.isEmpty())) {
                    addSkillsToDocument(table, hardSkills, softSkills);
                }

                if (courses != null && !courses.isEmpty()) {
                    addCoursesToDocument(table, courses);
                }

                if (projects != null && !projects.isEmpty()) {
                    addProjectsToDocument(table, projects);
                }

                if (educations != null && !educations.isEmpty()) {
                    addEducationsToDocument(table, educations);
                }

                if (teachingAssistance != null && !teachingAssistance.isEmpty()) {
                    addTeachingAssistanceToDocument(table, teachingAssistance);
                }

                if (presentations != null && !presentations.isEmpty()) {
                    addPresentationsToDocument(table, presentations);
                }

                if (patents != null && !patents.isEmpty()) {
                    addPatentsToDocument(table, patents);
                }

                if (researches != null && !researches.isEmpty()) {
                    addResearchesToDocument(table, researches);
                }

                if (languages != null && !languages.isEmpty()) {
                    addLanguagesToDocument(table, languages);
                }

                if (hobbies != null && !hobbies.isEmpty()) {
                    addHobbiesToDocument(table, hobbies);
                }

                if (memberships != null && !memberships.isEmpty()) {
                    addMembershipsToDocument(table, memberships);
                }

                if (volunteerActivities != null && !volunteerActivities.isEmpty()) {
                    addVolunteerActivitiesToDocument(table, volunteerActivities);
                }

                FileOutputStream out = new FileOutputStream(STORE_PATH + "\\SF Test.docx");
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

    private void createNameTitle(XWPFDocument document, PersonalInformation personalInformation) {
        XWPFParagraph nameParagraph = document.createParagraph();
        nameParagraph.setAlignment(ParagraphAlignment.CENTER);
        addRunToParagraph(nameParagraph, personalInformation.getFullName(), NAME_HEADING_FONT, true);
    }

    private void addContactInformationToDocument(XWPFDocument document, List<ContactMethod> contactInformation) {
        XWPFParagraph contactParagraph = document.createParagraph();
        contactParagraph.setAlignment(ParagraphAlignment.CENTER);

        int methodNumber = 0;
        for (ContactMethod method : contactInformation) {
            methodNumber++;
            addRunToParagraph(contactParagraph, method.getContent(), BODY_FONT, false);

            if (methodNumber < contactInformation.size()) {
                addSymbolToParagraph(contactParagraph, BULLET, BODY_FONT.getSize());
            }
        }

        insertNewLine(contactParagraph);
    }

    private void addSummary(XWPFTable table, Summary summary) {
        XWPFTableRow row = createRow(table);
        writeText(createCell(row, TWO_INCH_WIDTH).addParagraph(), "Summary", HEADING_FONT, true);

        XWPFTableCell bodyCell = createCell(row, FIVE_INCH_WIDTH);

        List<String> paragraphs = Arrays.stream(summary.getText().split("\\n")).toList();
        addRunToParagraph(bodyCell.addParagraph(), paragraphs, BODY_FONT, NEW_LINE, false);
        insertEmptyRow(table);
    }

    public static void addJobExperiencesToDocument(XWPFTable table, List<JobExperience> jobExperiences) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Experiences", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (JobExperience job : jobExperiences) {
            String jobDuration = calculateDuration(job.getStartDate(), job.getEndDate());
            writeText(bodyCell.addParagraph(), jobDuration, DATE_FONT, false);

            XWPFParagraph titleParagraph = bodyCell.addParagraph();
            List<String> titleParts = Arrays.asList(job.getTitle(),
                    job.getCompanyName(),
                    job.getLocation().getCityName().toString());
            List<String> split = Arrays.stream(job.getDescription().split("\\n")).toList();

            addRunToParagraph(titleParagraph, titleParts, TITLE_FONT, PIPE, true);
            XWPFParagraph paragraph = bodyCell.addParagraph();
            addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, false);
            insertNewLine(paragraph);
        }

        insertEmptyRow(table);
    }

    private static void addFormerColleaguesToDocument(XWPFTable table, List<FormerColleague> formerColleagues) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Former Colleagues", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        for (FormerColleague formerColleague : formerColleagues) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();
            List<String> titleParts = Arrays.asList(formerColleague.getFullName(),
                    formerColleague.getPosition(),
                    formerColleague.getPhoneNumber());

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, BULLET, false);
        }

        insertEmptyRow(table);
    }

    private static void addSkillsToDocument(XWPFTable table, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Skills", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        List<String> skills = new LinkedList<>();

        if (hardSkills != null) {
            skills = hardSkills.stream().map(h -> h.getType().toString()).toList();
        }

        if (softSkills != null) {
            List<String> softSkillList = softSkills.stream().map(SoftSkill::getTitle).toList();
            skills = Stream.concat(skills.stream(), softSkillList.stream()).toList();
        }

        XWPFParagraph titleParagraph = bodyCell.addParagraph();
        addRunToParagraph(titleParagraph, skills, BODY_FONT, BULLET, false);

        insertEmptyRow(table);
    }

    private static void addCoursesToDocument(XWPFTable table, List<Course> courses) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Courses", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (Course course : courses) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();
            List<String> titleParts = Arrays.asList(course.getName(), course.getInstitute());

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, false);
        }

        insertEmptyRow(table);
    }

    private static void addProjectsToDocument(XWPFTable table, List<Project> projects) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Projects", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (Project project : projects) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();
            String projectDuration = calculateDuration(project.getStartDate(), project.getEndDate());

            Map<String, String> titleParts = new LinkedHashMap<>() {{
                put(project.getName(), project.getReferenceLink());
                put(projectDuration, project.getReferenceLink());
                put(project.getStatus().toString(), project.getReferenceLink());
            }};

            addHyperlinkRunToParagraph(titleParagraph, titleParts, TITLE_FONT, PIPE, true);

            int projectNumber = 0;
            String[] descriptionText = project.getDescription().split("\\n");
            for (String descriptionBlock : descriptionText) {
                projectNumber++;
                XWPFParagraph descriptionParagraph = bodyCell.addParagraph();
                addRunToParagraph(descriptionParagraph, descriptionBlock, BODY_FONT, false);

                if (projectNumber == descriptionText.length)
                    insertNewLine(descriptionParagraph);
            }
        }

        insertEmptyRow(table);
    }

    private static void addEducationsToDocument(XWPFTable table, List<Education> educations) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Education", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (Education education : educations) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();
            String major = education.getDegreeLevel() + " of " + education.getMajor();
            String educationDuration = calculateYearDuration(education.getStartYear(), education.getEndYear());

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            List<String> titleParts = Arrays.asList(education.getDegreeLevel() + " of " + education.getMajor(),
                    major,
                    education.getUniversity(),
                    educationDuration);
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, false);
        }

        insertEmptyRow(table);
    }

    private static void addTeachingAssistanceToDocument(XWPFTable table, List<TeachingAssistance> teachingAssistanceList) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Teaching Assistance", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (TeachingAssistance ta : teachingAssistanceList) {
            XWPFParagraph paragraph = bodyCell.addParagraph();
            String duration = calculateDuration(ta.getStartDate(), ta.getEndDate());
            List<String> titleParts = Arrays.asList(ta.getTitle(),
                    ta.getUniversity(),
                    duration);

            addSymbolToParagraph(paragraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(paragraph, titleParts, BODY_FONT, PIPE, false);
        }

        insertEmptyRow(table);
    }

    private static void addPresentationsToDocument(XWPFTable table, List<Presentation> presentations) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Presentations", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (Presentation presentation : presentations) {
            XWPFParagraph presentationParagraph = bodyCell.addParagraph();

            List<String> titlePArt = Arrays.asList(presentation.getTitle(), presentation.getDate().toString());

            addSymbolToParagraph(presentationParagraph, DASH, TITLE_FONT.getSize());
            addRunToParagraph(presentationParagraph, titlePArt, BODY_FONT, PIPE, true);

            String[] descriptionText = presentation.getDescription().split("\\n");
            for (String descriptionParagraph : descriptionText) {
                XWPFParagraph paragraph = bodyCell.addParagraph();
                addRunToParagraph(paragraph, descriptionParagraph, BODY_FONT, false);
                insertNewLine(paragraph);
            }
        }

        insertEmptyRow(table);
    }

    private static void addPatentsToDocument(XWPFTable table, List<Patent> patents) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Patents", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (Patent patent : patents) {
            XWPFParagraph patentParagraph = bodyCell.addParagraph();
            List<String> titleParts = Arrays.asList(patent.getTitle(), patent.getRegistrationNumber(), patent.getRegistrationDate().toString());

            addSymbolToParagraph(patentParagraph, DASH, TITLE_FONT.getSize());
            addRunToParagraph(patentParagraph, titleParts, BODY_FONT, PIPE, true);

            String[] descriptionText = patent.getDescription().split("\\n");
            for (String descriptionParagraph : descriptionText) {
                XWPFParagraph paragraph = bodyCell.addParagraph();
                addRunToParagraph(paragraph, descriptionParagraph, BODY_FONT, false);
                insertNewLine(paragraph);
            }
        }

        insertEmptyRow(table);
    }

    private static void addResearchesToDocument(XWPFTable table, List<Research> researches) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Researches", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (Research research : researches) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();
            XWPFParagraph bodyParagraph = bodyCell.addParagraph();

            Map<String, String> titleParts = new LinkedHashMap<>() {{

                addSymbolToParagraph(titleParagraph, DASH, TITLE_FONT.getSize());
                put(research.getTitle(), research.getReferenceLink());
                put(research.getPublisher(), research.getReferenceLink());
                put(research.getDate() + " (Click to open the research)", research.getReferenceLink());
            }};
            List<String> descriptionText = Arrays.stream(research.getDescription().split("\\n")).toList();

            addHyperlinkRunToParagraph(titleParagraph, titleParts, TITLE_FONT, PIPE, true);
            addRunToParagraph(bodyParagraph, descriptionText, BODY_FONT, NEW_LINE, false);
            insertNewLine(bodyParagraph);
        }

        insertEmptyRow(table);
    }

    private static void addLanguagesToDocument(XWPFTable table, List<Language> languages) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Languages", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        XWPFParagraph titleParagraph = bodyCell.addParagraph();
        int languageNumber = 0;
        for (Language language : languages) {
            languageNumber++;
            addRunToParagraph(titleParagraph, language.getName().toString(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, COLON, BODY_FONT.getSize());

            addRunToParagraph(titleParagraph, language.estimateAverageLevel().toString(), BODY_FONT, false);

            if (languageNumber < languages.size())
                addSymbolToParagraph(titleParagraph, BULLET, BODY_FONT.getSize());
        }

        insertEmptyRow(table);
    }

    private static void addHobbiesToDocument(XWPFTable table, List<Hobby> hobbies) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Hobbies", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        XWPFParagraph titleParagraph = bodyCell.addParagraph();

        List<String> hobbyList = new LinkedList<>();
        for (Hobby hobby : hobbies) {
            hobbyList.add(hobby.getTitle());
        }

        addRunToParagraph(titleParagraph, hobbyList, BODY_FONT, BULLET, false);
        insertEmptyRow(table);
    }

    private static void addMembershipsToDocument(XWPFTable table, List<Membership> memberships) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Memberships", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (Membership membership : memberships) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();
            List<String> titleParts = Arrays.asList(membership.getTitle(), membership.getDate().toString());

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, false);
        }

        insertEmptyRow(table);
    }

    private static void addVolunteerActivitiesToDocument(XWPFTable
                                                                 table, List<VolunteerActivity> volunteerActivities) {
        XWPFTableRow row = table.createRow();
        writeText(getRowCell(row, 0).addParagraph(), "Volunteer Activities", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);

        for (VolunteerActivity activity : volunteerActivities) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();
            List<String> titleParts = Arrays.asList(activity.getTitle(), valueOf(activity.getYear()));

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, false);
        }

        insertEmptyRow(table);
    }
}
